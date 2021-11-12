package com.rsba.component_microservice.data.service.usecase.technologies

import com.rsba.component_microservice.data.dao.OperationDao
import com.rsba.component_microservice.data.service.usecase.queries.TechnologyQueries
import com.rsba.component_microservice.domain.queries.QueryCursor
import com.rsba.component_microservice.domain.model.Operation
import com.rsba.component_microservice.domain.usecase.custom.technology.RetrieveOperationTechnologiesUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.util.*

@Component
@OptIn(ExperimentalSerializationApi::class)
class RetrieveOperationTechnologiesUseCaseImpl : RetrieveOperationTechnologiesUseCase {

    override suspend fun invoke(
        database: DatabaseClient,
        ids: Set<UUID>,
        first: Int,
        after: UUID?,
        token: UUID
    ): Map<UUID, List<Operation>> =
        Flux.fromIterable(ids)
            .parallel()
            .flatMap { id ->
                database.sql(TechnologyQueries.operations(token = token, id = id, first = first, after = after))
                    .map { row -> QueryCursor.all(row = row) }
                    .first()
                    .map { it?.mapNotNull { element -> (element as? OperationDao?)?.to } ?: emptyList() }
                    .map { AbstractMap.SimpleEntry(id, it) }
                    .onErrorResume { throw it }
            }
            .runOn(Schedulers.parallel())
            .sequential()
            .collectList()
            .map { entries -> entries.associateBy({ it.key }, { it.value ?: emptyList() }) }
            .onErrorResume { throw it }
            .log()
            .awaitFirstOrElse { emptyMap() }
}