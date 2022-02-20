package com.rsba.tasks_microservice.data.service.usecase.tasks

import com.rsba.tasks_microservice.data.service.usecase.queries.TaskQueries
import com.rsba.tasks_microservice.domain.queries.QueryCursor
import com.rsba.tasks_microservice.domain.usecase.custom.task.ToExecuteTasksOfItemUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import java.util.*

@Component
@OptIn(ExperimentalSerializationApi::class)
class ToExecuteTasksOfItemUseCaseImpl(private val database: DatabaseClient) : ToExecuteTasksOfItemUseCase {
    override suspend fun invoke(input: UUID, token: UUID): Boolean =
        database.sql(TaskQueries.executeTasksOfItem(input = input, token = token))
            .map { row -> QueryCursor.isTrue(row = row) }
            .first()
            .onErrorResume { throw it }
            .log()
            .awaitFirstOrElse { false }
}