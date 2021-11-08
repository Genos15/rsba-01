package com.rsba.component_microservice.domain.usecase.custom.item_category

import com.rsba.component_microservice.domain.model.ItemCategory
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface RetrieveItemCategoryChildrenDataLoaderUseCase {
    suspend operator fun invoke(database: DatabaseClient, ids: Set<UUID>, token: UUID): Map<UUID, List<ItemCategory>>
}