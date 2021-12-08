package com.rsba.order_microservice.data.service.usecase.tasks

import com.rsba.order_microservice.data.dao.GroupDao
import com.rsba.order_microservice.data.service.usecase.queries.TaskQueries
import com.rsba.order_microservice.domain.model.Group
import com.rsba.order_microservice.domain.queries.QueryCursor
import com.rsba.order_microservice.domain.usecase.custom.task.RetrieveDepartmentsUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.util.*

@Component
@OptIn(ExperimentalSerializationApi::class)
class RetrieveDepartmentsUseCaseImpl : RetrieveDepartmentsUseCase {

    override suspend fun invoke(
        database: DatabaseClient,
        ids: Set<UUID>,
        first: Int,
        after: UUID?,
        token: UUID
    ): Map<UUID, List<Group>> =
        Flux.fromIterable(ids)
            .parallel()
            .flatMap { id ->
                database.sql(
                    TaskQueries.departments(
                        token = token,
                        id = id,
                        first = first,
                        after = after,
                    )
                ).map { row -> QueryCursor.all(row = row) }
                    .first()
                    .map { it?.mapNotNull { element -> (element as? GroupDao?)?.to } ?: emptyList() }
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