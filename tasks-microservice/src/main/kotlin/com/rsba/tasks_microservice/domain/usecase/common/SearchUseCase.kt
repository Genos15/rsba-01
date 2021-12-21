package com.rsba.tasks_microservice.domain.usecase.common

import com.rsba.tasks_microservice.domain.model.TaskLayer
import com.rsba.tasks_microservice.domain.model.TaskStatus
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface SearchUseCase<T> {
    suspend operator fun invoke(
        database: DatabaseClient,
        input: String,
        first: Int,
        after: UUID? = null,
        status: TaskStatus? = null,
        layer: TaskLayer? = null,
        id: UUID? = null,
        token: UUID
    ): List<T>
}