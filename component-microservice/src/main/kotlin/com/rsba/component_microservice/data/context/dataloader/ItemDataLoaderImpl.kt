package com.rsba.component_microservice.data.context.dataloader

import com.rsba.component_microservice.domain.model.ItemCategory
import com.rsba.component_microservice.domain.model.Operation
import com.rsba.component_microservice.domain.repository.ItemRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import mu.KLogger
import org.dataloader.DataLoader
import org.springframework.stereotype.Service
import java.util.*

@Service
class ItemDataLoaderImpl(private val logger: KLogger, private val service: ItemRepository) {

    fun dataLoaderOperationInItem(userId: UUID): DataLoader<UUID, List<Operation>> =
        DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future {
                service.retrieveOperationInItem(
                    ids = ids,
                    userId = userId,
                    page = 0,
                    size = 1000
                )
            }
        }

    fun dataLoaderCategoryInItem(userId: UUID): DataLoader<UUID, ItemCategory?> =
        DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future {
                service.retrieveCategoryInItem(
                    ids = ids,
                    userId = userId,
                    page = 0,
                    size = 1000
                )
            }
        }

}