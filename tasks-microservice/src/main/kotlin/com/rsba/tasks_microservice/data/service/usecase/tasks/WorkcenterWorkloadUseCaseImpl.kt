package com.rsba.tasks_microservice.data.service.usecase.tasks

import com.rsba.tasks_microservice.data.service.usecase.queries.TaskQueries
import com.rsba.tasks_microservice.domain.queries.QueryCursor
import com.rsba.tasks_microservice.domain.usecase.custom.task.WorkcenterWorkloadUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import java.time.OffsetDateTime
import java.util.*

@OptIn(ExperimentalSerializationApi::class)
@Component("find_workcenter_workload")
class WorkcenterWorkloadUseCaseImpl(private val database: DatabaseClient) : WorkcenterWorkloadUseCase {

    override suspend fun invoke(id: UUID, rangeStart: OffsetDateTime, rangeEnd: OffsetDateTime, token: UUID): Float =
        database.sql(
            TaskQueries.workcenterWorkload(
                token = token,
                id = id,
                rangeStart = rangeStart,
                rangeEnd = rangeEnd
            )
        )
            .map { row -> QueryCursor.howMuch(row = row) }
            .first()
            .onErrorResume { throw it }
            .log()
            .awaitFirstOrElse { 0f }

}