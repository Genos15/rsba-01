package com.rsba.tasks_microservice.data.service.usecase.tasks

import com.rsba.tasks_microservice.data.dao.TaskDao
import com.rsba.tasks_microservice.data.service.usecase.queries.TaskQueries
import com.rsba.tasks_microservice.domain.input.TaskWorkerTimeInput
import com.rsba.tasks_microservice.domain.model.MutationAction
import com.rsba.tasks_microservice.domain.model.Task
import com.rsba.tasks_microservice.domain.queries.QueryCursor
import com.rsba.tasks_microservice.domain.usecase.custom.task.ToAllocateUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import java.util.*

@Component
@OptIn(ExperimentalSerializationApi::class)
class ToAllocateUseCaseImpl : ToAllocateUseCase {
    override suspend fun invoke(
        database: DatabaseClient,
        id: UUID,
        users: List<TaskWorkerTimeInput>,
        action: MutationAction?,
        token: UUID
    ): Optional<Task> =
        database.sql(TaskQueries.allocate(id = id, users = users, action = action, token = token))
            .map { row -> QueryCursor.one(row = row) }
            .first()
            .map { Optional.ofNullable((it.orElseGet { null } as? TaskDao?)?.to) }
            .onErrorResume { throw it }
            .log()
            .awaitFirstOrElse { Optional.empty() }
}