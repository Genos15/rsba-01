package com.rsba.component_microservice.data.context.dataloader

import com.rsba.component_microservice.domain.model.ItemCategory
import com.rsba.component_microservice.domain.repository.ItemCategoryRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import org.dataloader.DataLoader
import org.springframework.stereotype.Component
import java.util.*

@Component
class ItemCategoryDataLoaderImpl(private val service: ItemCategoryRepository) {

    fun dataLoaderSubItemCategory(userId: UUID): DataLoader<UUID, List<ItemCategory>> =
        DataLoader.newMappedDataLoader { ids -> GlobalScope.future { service.children(ids = ids) } }

}