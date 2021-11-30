package com.rsba.order_microservice.data.context.dataloader

import com.rsba.order_microservice.domain.model.ItemCategory
import com.rsba.order_microservice.domain.model.CategoryOfItemInOrder
import com.rsba.order_microservice.domain.model.Item
import com.rsba.order_microservice.domain.repository.OrderRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import mu.KLogger
import org.dataloader.DataLoader
import org.springframework.stereotype.Component
import java.util.*

@Component
class CategoryOfItemDataLoaderImpl(private val logger: KLogger, private val service: OrderRepository) {

//    fun dataLoaderCategoriesOfItemInOrder(userId: UUID): DataLoader<UUID, List<CategoryOfItemInOrder>> {
//        logger.warn { "+--- CategoryOfItemDataLoaderImpl -> dataLoaderCategoriesOfItemInOrder" }
//        return DataLoader.newMappedDataLoader { ids, env ->
//            logger.warn { env }
//            GlobalScope.future {
//                service.retrieveCategoriesOfOrder(
//                    orderIds = ids,
//                    userId = userId,
//                    page = 0,
//                    size = 1000
//                )
//            }
//        }
//    }

//    fun dataLoaderItemsInOrder(userId: UUID): DataLoader<ItemCategory, List<Item>> {
//        logger.warn { "+--- CategoryOfItemDataLoaderImpl -> dataLoaderItemsInOrder" }
//        return DataLoader.newMappedDataLoader { ids, env ->
//            logger.warn { env }
//            GlobalScope.future {
//                service.onRetrieveItemsInCategoriesOfOrder(
//                    ids = ids,
//                    moduleId = userId,
//                    page = 0,
//                    size = 1000
//                )
//            }
//        }
//    }
}