package com.rsba.order_microservice.domain.usecase.custom.order

import com.rsba.order_microservice.domain.model.ItemCategoryStatistics
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface RetrieveItemCategoryStatisticsUseCase {
    suspend operator fun invoke(
        database: DatabaseClient,
        ids: Set<UUID>,
        first: Int,
        after: UUID? = null,
        token: UUID
    ): Map<UUID, List<ItemCategoryStatistics>>
}