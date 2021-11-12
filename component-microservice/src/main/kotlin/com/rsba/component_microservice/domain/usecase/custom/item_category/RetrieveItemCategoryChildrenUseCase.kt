package com.rsba.component_microservice.domain.usecase.custom.item_category

import com.rsba.component_microservice.domain.model.ItemCategory
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface RetrieveItemCategoryChildrenUseCase {
    suspend operator fun invoke(
        database: DatabaseClient,
        ids: Set<UUID>,
        first: Int,
        after: UUID?,
        token: UUID
    ): Map<UUID, List<ItemCategory>>
}