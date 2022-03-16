package com.rsba.tasks_microservice.data.service.usecase.task_set

import com.rsba.tasks_microservice.data.dao.TaskSetDao
import com.rsba.tasks_microservice.domain.queries.QueryCursor
import com.rsba.tasks_microservice.domain.model.TaskLayer
import com.rsba.tasks_microservice.domain.model.TaskSet
import com.rsba.tasks_microservice.domain.model.TaskStatus
import com.rsba.tasks_microservice.domain.queries.IQueryGuesser
import com.rsba.tasks_microservice.domain.queries.query
 import com.rsba.tasks_microservice.domain.usecase.common.RetrieveUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import java.time.OffsetDateTime
import java.util.*

@Component(value = "retrieve_tasks_set")
@OptIn(ExperimentalSerializationApi::class)
class RetrieveUseCaseImpl(private val database: DatabaseClient) : RetrieveUseCase<TaskSet>, IQueryGuesser {
    override suspend fun invoke(
        database: DatabaseClient,
        first: Int,
        after: UUID?,
        status: TaskStatus?,
        layer: TaskLayer?,
        id: UUID?,
        rangeStart: OffsetDateTime?,
        rangeEnd: OffsetDateTime?,
        token: UUID
    ): List<TaskSet> = emptyList()

    override suspend fun invoke(
        first: Int,
        after: UUID?,
        rangeStart: OffsetDateTime?,
        rangeEnd: OffsetDateTime?,
        id: UUID?,
        token: UUID
    ): List<TaskSet> = database.sql(
        query<TaskSetDao>().retrieve(
            token = token,
            first = first,
            after = after,
            rangeEnd = rangeEnd,
            rangeStart = rangeStart,
            id = id
        )
    )
        .map { row -> QueryCursor.all(row = row) }
        .first()
        .map { it?.mapNotNull { element -> (element as? TaskSetDao?)?.to } ?: emptyList() }
        .onErrorResume {
            throw it
        }
        .awaitFirstOrElse { emptyList() }
}