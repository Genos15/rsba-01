package com.rsba.tasks_microservice.data.service.usecase.tasks

import com.rsba.tasks_microservice.data.dao.TaskDao
import com.rsba.tasks_microservice.domain.queries.QueryCursor
import com.rsba.tasks_microservice.domain.model.Task
import com.rsba.tasks_microservice.domain.model.TaskLayer
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

@Component(value = "retrieve_tasks")
@OptIn(ExperimentalSerializationApi::class)
class RetrieveUseCaseImpl : RetrieveUseCase<Task>, IQueryGuesser {
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
    ): List<Task> =
        database.sql(
            query<TaskDao>().retrieve(
                token = token,
                first = first,
                after = after,
                status = status,
                layer = layer,
                id = id,
                rangeEnd = rangeEnd,
                rangeStart = rangeStart,
            )
        )
            .map { row -> QueryCursor.all(row = row) }
            .first()
            .map { it?.mapNotNull { element -> (element as? TaskDao?)?.to } ?: emptyList() }
            .onErrorResume {
                throw it
            }
            .awaitFirstOrElse { emptyList() }

    override suspend fun invoke(
        first: Int,
        after: UUID?,
        rangeStart: OffsetDateTime?,
        rangeEnd: OffsetDateTime?,
        id: UUID?,
        token: UUID
    ): List<Task> = emptyList()
}