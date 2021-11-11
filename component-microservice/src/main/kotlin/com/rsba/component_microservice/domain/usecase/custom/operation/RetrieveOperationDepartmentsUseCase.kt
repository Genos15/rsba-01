package com.rsba.component_microservice.domain.usecase.custom.operation

import com.rsba.component_microservice.domain.model.Group
import org.springframework.r2dbc.core.DatabaseClient
 import java.util.*

interface RetrieveOperationDepartmentsUseCase {
    suspend operator fun invoke(
        database: DatabaseClient,
        ids: Set<UUID>,
        first: Int,
        after: UUID?,
        token: UUID
    ): Map<UUID, List<Group>>
}