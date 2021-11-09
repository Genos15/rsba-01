package com.rsba.component_microservice.data.context.dataloader

import com.rsba.component_microservice.domain.model.Item
import com.rsba.component_microservice.domain.model.ItemCategory
import com.rsba.component_microservice.domain.model.Operation
import com.rsba.component_microservice.domain.repository.ItemRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import org.dataloader.DataLoader
import org.springframework.stereotype.Component
import java.util.*

@Component
class ItemDataLoaderImpl(private val service: ItemRepository) {

    fun dataLoaderOperation(userId: UUID): DataLoader<UUID, List<Operation>> =
        DataLoader.newMappedDataLoader { ids -> GlobalScope.future { service.operations(ids = ids) } }

    fun dataLoaderCategory(userId: UUID): DataLoader<UUID, Optional<ItemCategory>> =
        DataLoader.newMappedDataLoader { ids -> GlobalScope.future { service.category(ids = ids) } }

    fun dataLoaderComponents(userId: UUID): DataLoader<UUID, List<Item>> =
        DataLoader.newMappedDataLoader { ids -> GlobalScope.future { service.components(ids = ids) } }

}