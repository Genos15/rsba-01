package com.rsba.tasks_microservice.data.service.usecase.task_set


import com.rsba.tasks_microservice.data.dao.TaskDao
import com.rsba.tasks_microservice.data.service.usecase.queries.TaskSetQueries
import com.rsba.tasks_microservice.domain.model.Task
import com.rsba.tasks_microservice.domain.queries.QueryCursor
import com.rsba.tasks_microservice.domain.usecase.common.RetrieveTasksUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.util.*

@Component(value = "retrieve_task_set_tasks")
@OptIn(ExperimentalSerializationApi::class)
class RetrieveTasksUseCaseImpl(private val database: DatabaseClient) : RetrieveTasksUseCase {
    override suspend fun invoke(ids: Set<UUID>, first: Int, after: UUID?, token: UUID): Map<UUID, List<Task>> =
        Flux.fromIterable(ids)
            .parallel()
            .flatMap { id ->
                database.sql(
                    TaskSetQueries.tasks(
                        token = token,
                        id = id,
                        first = first,
                        after = after,
                    )
                ).map { row -> QueryCursor.all(row = row) }
                    .first()
                    .map { it?.mapNotNull { element -> (element as? TaskDao?)?.to } ?: emptyList() }
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