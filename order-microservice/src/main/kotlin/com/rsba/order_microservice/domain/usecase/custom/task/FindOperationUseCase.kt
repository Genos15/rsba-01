package com.rsba.order_microservice.domain.usecase.custom.task

import com.rsba.order_microservice.domain.model.Operation
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface FindOperationUseCase {
    suspend operator fun invoke(
        database: DatabaseClient,
        ids: Set<UUID>,
        token: UUID
    ): Map<UUID, Optional<Operation>>
}