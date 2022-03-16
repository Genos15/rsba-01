package com.rsba.tasks_microservice.data.service.usecase.task_set

import com.rsba.tasks_microservice.data.dao.TaskSetDao
import com.rsba.tasks_microservice.domain.exception.CustomGraphQLError
import com.rsba.tasks_microservice.domain.queries.QueryCursor
import com.rsba.tasks_microservice.domain.model.TaskSet
import com.rsba.tasks_microservice.domain.queries.IQueryGuesser
import com.rsba.tasks_microservice.domain.queries.query
 import com.rsba.tasks_microservice.domain.usecase.common.FindUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.*

@Component(value = "find_tasks_set")
@OptIn(ExperimentalSerializationApi::class)
class FindUseCaseImpl(private val database: DatabaseClient) : FindUseCase<TaskSet>, IQueryGuesser {
    override suspend fun invoke(database: DatabaseClient, id: UUID, token: UUID): Optional<TaskSet> = Optional.empty()

    override suspend fun invoke(id: UUID, token: UUID): Optional<TaskSet> =
        database.sql(query<TaskSetDao>().find(id = id, token = token))
            .map { row -> QueryCursor.one(row = row) }
            .first()
            .map { Optional.ofNullable((it.orElseGet { null } as? TaskSetDao?)?.to) }
            .onErrorResume {
                if (it is CustomGraphQLError) {
                    Mono.empty()
                } else {
                    throw it
                }
            }
            .log()
            .awaitFirstOrElse { Optional.empty() }
}