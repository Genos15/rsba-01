package com.rsba.order_microservice.domain.usecase.custom.task

import com.rsba.order_microservice.domain.model.Group
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface RetrieveDepartmentsUseCase {
    suspend operator fun invoke(
        database: DatabaseClient,
        ids: Set<UUID>,
        first: Int,
        after: UUID? = null,
        token: UUID
    ): Map<UUID, List<Group>>
}