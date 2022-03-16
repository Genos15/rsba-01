package com.rsba.tasks_microservice.domain.usecase.common.custom.task

import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface RetrieveValuesUseCase {
    suspend operator fun invoke(
        database: DatabaseClient,
        ids: Set<UUID>,
        first: Int,
        after: UUID?,
        token: UUID
    ): Map<UUID, List<String>>
}