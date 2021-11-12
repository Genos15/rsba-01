package com.rsba.component_microservice.domain.usecase.custom.technology

import com.rsba.component_microservice.domain.model.Operation
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface RetrieveOperationTechnologiesUseCase {
    suspend operator fun invoke(
        database: DatabaseClient,
        ids: Set<UUID>,
        first: Int,
        after: UUID?,
        token: UUID
    ): Map<UUID, List<Operation>>
}