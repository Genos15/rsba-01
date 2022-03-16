package com.rsba.tasks_microservice.data.service.usecase.task_set

import com.rsba.tasks_microservice.data.dao.TaskSetDao
import com.rsba.tasks_microservice.data.service.usecase.queries.TaskSetQueries
import com.rsba.tasks_microservice.domain.model.TaskSet
import com.rsba.tasks_microservice.domain.queries.QueryCursor
import com.rsba.tasks_microservice.domain.usecase.common.custom.task_set.ToExecuteTaskSetUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import java.util.*

@Component
@OptIn(ExperimentalSerializationApi::class)
class ToExecuteTaskSetUseCaseImpl(private val database: DatabaseClient) : ToExecuteTaskSetUseCase {
    override suspend fun invoke(id: UUID, quantity: Int?, token: UUID): Optional<TaskSet> =
        database.sql(TaskSetQueries.execute(id = id, quantity = quantity, token = token))
            .map { row -> QueryCursor.one(row = row) }
            .first()
            .map { Optional.ofNullable((it.orElseGet { null } as? TaskSetDao?)?.to) }
            .onErrorResume { throw it }
            .log()
            .awaitFirstOrElse { Optional.empty() }
}