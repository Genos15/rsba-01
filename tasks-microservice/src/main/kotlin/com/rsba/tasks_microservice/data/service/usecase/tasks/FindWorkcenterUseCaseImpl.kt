package com.rsba.tasks_microservice.data.service.usecase.tasks

import com.rsba.tasks_microservice.data.dao.WorkcenterDao
import com.rsba.tasks_microservice.data.service.usecase.queries.TaskQueries
import com.rsba.tasks_microservice.domain.model.Workcenter
import com.rsba.tasks_microservice.domain.queries.QueryCursor
import com.rsba.tasks_microservice.domain.usecase.custom.task.FindWorkcenterUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.util.*

@Component
@OptIn(ExperimentalSerializationApi::class)
class FindWorkcenterUseCaseImpl : FindWorkcenterUseCase {
    override suspend fun invoke(
        database: DatabaseClient,
        ids: Set<UUID>,
        token: UUID
    ): Map<UUID, Optional<Workcenter>> =
        Flux.fromIterable(ids)
            .parallel()
            .flatMap { id ->
                database.sql(TaskQueries.workcenter(token = token, id = id))
                    .map { row -> QueryCursor.one(row = row) }
                    .first()
                    .map { Optional.ofNullable((it.orElseGet { null } as? WorkcenterDao?)?.to) }
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