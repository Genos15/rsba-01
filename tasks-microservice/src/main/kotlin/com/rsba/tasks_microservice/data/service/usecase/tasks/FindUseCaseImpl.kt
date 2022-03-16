package com.rsba.tasks_microservice.data.service.usecase.tasks

import com.rsba.tasks_microservice.data.dao.TaskDao
import com.rsba.tasks_microservice.domain.exception.CustomGraphQLError
import com.rsba.tasks_microservice.domain.queries.QueryCursor
import com.rsba.tasks_microservice.domain.model.Task
import com.rsba.tasks_microservice.domain.queries.IQueryGuesser
import com.rsba.tasks_microservice.domain.queries.query
import com.rsba.tasks_microservice.domain.usecase.common.FindUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.*

@Component(value = "find_task")
@OptIn(ExperimentalSerializationApi::class)
class FindUseCaseImpl : FindUseCase<Task>, IQueryGuesser {
    override suspend fun invoke(database: DatabaseClient, id: UUID, token: UUID): Optional<Task> =
        database.sql(query<TaskDao>().find(id = id, token = token))
            .map { row -> QueryCursor.one(row = row) }
            .first()
            .map { Optional.ofNullable((it.orElseGet { null } as? TaskDao?)?.to) }
            .onErrorResume {
                if (it is CustomGraphQLError) {
                    Mono.empty()
                } else {
                    throw it
                }
            }
            .log()
            .awaitFirstOrElse { Optional.empty() }

    override suspend fun invoke(id: UUID, token: UUID): Optional<Task> = Optional.empty()
}