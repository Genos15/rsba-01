package com.rsba.parameters_microservice.domain.usecase.custom.parameter

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