package com.rsba.tasks_microservice.data.service.usecase.task_set


import com.rsba.tasks_microservice.data.dao.UserDao
import com.rsba.tasks_microservice.data.service.usecase.queries.TaskSetQueries
import com.rsba.tasks_microservice.domain.model.User
import com.rsba.tasks_microservice.domain.queries.QueryCursor
import com.rsba.tasks_microservice.domain.usecase.common.RetrieveUsersUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.util.*

@Component(value = "retrieve_task_set_users")
@OptIn(ExperimentalSerializationApi::class)
class RetrieveUsersUseCaseImpl(private val database: DatabaseClient) : RetrieveUsersUseCase {
    override suspend fun invoke(ids: Set<UUID>, first: Int, after: UUID?, token: UUID): Map<UUID, List<User>> =
        Flux.fromIterable(ids)
            .parallel()
            .flatMap { id ->
                database.sql(
                    TaskSetQueries.users(
                        token = token,
                        id = id,
                        first = first,
                        after = after,
                    )
                ).map { row -> QueryCursor.all(row = row) }
                    .first()
                    .map { it?.mapNotNull { element -> (element as? UserDao?)?.to } ?: emptyList() }
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