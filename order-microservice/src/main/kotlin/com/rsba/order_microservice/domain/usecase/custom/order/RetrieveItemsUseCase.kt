package com.rsba.order_microservice.domain.usecase.custom.order

import com.rsba.order_microservice.domain.model.Item
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface RetrieveItemsUseCase {
    suspend operator fun invoke(
        database: DatabaseClient,
        ids: Set<UUID>,
        first: Int,
        parentId: UUID? = null,
        after: UUID? = null,
        token: UUID
    ): Map<UUID, List<Item>>
}