package com.rsba.tasks_microservice.domain.usecase.common

import com.rsba.tasks_microservice.domain.model.TaskLayer
import com.rsba.tasks_microservice.domain.model.TaskStatus
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface CountUseCase {
    suspend operator fun invoke(
        database: DatabaseClient,
        status: TaskStatus? = null,
        layer: TaskLayer? = null,
        id: UUID? = null,
        token: UUID
    ): Int

    suspend operator fun invoke(token: UUID): Int
}