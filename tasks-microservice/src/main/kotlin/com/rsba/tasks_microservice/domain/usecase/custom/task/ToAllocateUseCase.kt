package com.rsba.tasks_microservice.domain.usecase.custom.task

import com.rsba.tasks_microservice.domain.input.TaskWorkerTimeInput
import com.rsba.tasks_microservice.domain.model.MutationAction
import com.rsba.tasks_microservice.domain.model.Task
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface ToAllocateUseCase {
    suspend operator fun invoke(
        database: DatabaseClient,
        id: UUID,
        users: List<TaskWorkerTimeInput>,
        action: MutationAction?,
        token: UUID
    ): Optional<Task>
}