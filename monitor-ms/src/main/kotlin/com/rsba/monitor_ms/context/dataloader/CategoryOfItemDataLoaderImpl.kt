package com.rsba.monitor_ms.context.dataloader

import com.rsba.monitor_ms.domain.model.CategoryOfItemInOrder
import com.rsba.monitor_ms.repository.OrderRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import mu.KLogger
import org.dataloader.DataLoader
import org.springframework.stereotype.Service
import java.util.*

@Service
class CategoryOfItemDataLoaderImpl(private val logger: KLogger, private val service: OrderRepository) {

    fun dataLoaderCategoriesOfItemInOrder(userId: UUID): DataLoader<UUID, List<CategoryOfItemInOrder>> {
        logger.warn { "+--- CategoryOfItemDataLoaderImpl -> dataLoaderCategoriesOfItemInOrder" }
        return DataLoader.newMappedDataLoader { ids, env ->
            logger.warn { env }
            GlobalScope.future {
                service.retrieveCategoriesOfOrder(
                    orderIds = ids,
                    userId = userId,
                    page = 0,
                    size = 1000
                )
            }
        }
    }

}