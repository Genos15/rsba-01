package com.rsba.order_microservice.domain.usecase.custom.order

import com.rsba.order_microservice.domain.model.OrderStatistics
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface FindOrderStatisticsUseCase {
    suspend operator fun invoke(
        database: DatabaseClient,
        ids: Set<UUID>,
        token: UUID
    ): Map<UUID, Optional<OrderStatistics>>
}