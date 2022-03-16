package com.rsba.tasks_microservice.data.service.usecase.task_set

import com.rsba.tasks_microservice.data.dao.TaskSetDao
import com.rsba.tasks_microservice.domain.model.TaskLayer
import com.rsba.tasks_microservice.domain.model.TaskStatus
import com.rsba.tasks_microservice.domain.queries.IQueryGuesser
import com.rsba.tasks_microservice.domain.queries.QueryCursor
import com.rsba.tasks_microservice.domain.queries.query
 import com.rsba.tasks_microservice.domain.usecase.common.CountUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import java.util.*

@OptIn(ExperimentalSerializationApi::class)
@Component(value = "count_tasks_set")
class CountUseCaseImpl(private val database: DatabaseClient) : CountUseCase, IQueryGuesser {
    override suspend fun invoke(
        database: DatabaseClient,
        status: TaskStatus?,
        layer: TaskLayer?,
        id: UUID?,
        token: UUID
    ): Int = 0

    override suspend fun invoke(token: UUID): Int =
        database.sql(query<TaskSetDao>().count(token = token))
            .map { row -> QueryCursor.count(row = row) }
            .first()
            .onErrorResume { throw it }
            .log()
            .awaitFirstOrElse { 0 }
}