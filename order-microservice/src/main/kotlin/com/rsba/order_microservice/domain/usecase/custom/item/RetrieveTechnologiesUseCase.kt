package com.rsba.order_microservice.domain.usecase.custom.item

import com.rsba.order_microservice.domain.model.Technology
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface RetrieveTechnologiesUseCase {
    suspend operator fun invoke(
        database: DatabaseClient,
        ids: Set<UUID>,
        first: Int,
        after: UUID? = null,
        token: UUID
    ): Map<UUID, List<Technology>>
}