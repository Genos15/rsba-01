package com.rsba.order_microservice.context.dataloader

import com.rsba.order_microservice.domain.model.Item
import com.rsba.order_microservice.domain.model.OrderType
import com.rsba.order_microservice.repository.OrderRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import mu.KLogger
import org.dataloader.DataLoader
import org.springframework.stereotype.Component
import java.util.*

@Component
class OrderDataLoaderImpl(private val logger: KLogger, private val service: OrderRepository) {

//    fun dataLoaderCategoriesOfItemInOrder(userId: UUID): DataLoader<UUID, List<CategoryOfItem>> {
//        logger.warn { "+OrderDataLoaderImpl -> dataLoaderCategoriesOfItemInOrder" }
//        return DataLoader.newMappedDataLoader { ids ->
//            GlobalScope.future {
//                service.retrieveCategoriesInOrder(
//                    ids = ids,
//                    userId = userId,
//                    page = 0,
//                    size = 1000
//                )
//            }
//        }
//    }

    fun dataLoaderItemsInOrder(userId: UUID): DataLoader<UUID, List<Item>> {
        logger.warn { "+OrderDataLoaderImpl -> dataLoaderItemsInOrder" }
        return DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future {
                service.retrieveItemsInOrder(
                    ids = ids,
                    userId = userId,
                    page = 0,
                    size = 1000
                )
            }
        }
    }


    fun dataLoaderTypeOfOrder(userId: UUID): DataLoader<UUID, Optional<OrderType>> {
        logger.warn { "+OrderDataLoaderImpl->dataLoaderTypeOfOrder" }
        return DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future {
                service.retrieveMyType(
                    ids = ids,
                    userId = userId,
                )
            }
        }
    }

}