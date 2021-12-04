package com.rsba.order_microservice.domain.usecase.custom.order

import com.rsba.order_microservice.domain.model.Task
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface RetrieveTasksUseCase {
    suspend operator fun invoke(
        database: DatabaseClient,
        ids: Set<UUID>,
        first: Int,
        parentId: UUID? = null,
        after: UUID? = null,
        token: UUID
    ): Map<UUID, List<Task>>
}