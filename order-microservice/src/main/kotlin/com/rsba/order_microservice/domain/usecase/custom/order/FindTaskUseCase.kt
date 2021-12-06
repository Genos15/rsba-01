package com.rsba.order_microservice.domain.usecase.custom.order

import com.rsba.order_microservice.domain.model.Task
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface FindTaskUseCase {
    suspend operator fun invoke(
        database: DatabaseClient,
        ids: Set<UUID>,
        token: UUID
    ): Map<UUID, Optional<Task>>
}