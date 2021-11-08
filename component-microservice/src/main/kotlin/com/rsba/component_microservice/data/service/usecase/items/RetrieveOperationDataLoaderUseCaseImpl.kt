package com.rsba.component_microservice.data.service.usecase.items

import com.rsba.component_microservice.data.dao.OperationDao
import com.rsba.component_microservice.data.service.usecase.queries.ItemQueries
import com.rsba.component_microservice.domain.format.QueryCursor
import com.rsba.component_microservice.domain.model.Operation
import com.rsba.component_microservice.domain.usecase.custom.item.RetrieveOperationDataLoaderUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.util.*


@Component
@OptIn(ExperimentalSerializationApi::class)
class RetrieveOperationDataLoaderUseCaseImpl : RetrieveOperationDataLoaderUseCase {
    override suspend fun invoke(database: DatabaseClient, ids: Set<UUID>, token: UUID): Map<UUID, List<Operation>> =
        Flux.fromIterable(ids)
            .parallel()
            .flatMap { id ->
                database.sql(ItemQueries.category(token = token, id = id))
                    .map { row -> QueryCursor.all(row = row) }
                    .first()
                    .map { it?.mapNotNull { element -> (element as? OperationDao?)?.to } ?: emptyList() }
                    .map { AbstractMap.SimpleEntry(id, it) }
                    .onErrorResume { throw it }
            }
            .runOn(Schedulers.parallel())
            .sequential()
            .collectList()
            .map { entries -> entries.associateBy({ it.key }, { it.value }) }
            .onErrorResume { throw it }
            .log()
            .awaitFirstOrElse { emptyMap() }
}


