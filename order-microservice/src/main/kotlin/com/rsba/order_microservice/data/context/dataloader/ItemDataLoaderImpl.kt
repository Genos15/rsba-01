package com.rsba.order_microservice.data.context.dataloader

import com.rsba.order_microservice.domain.model.*
import com.rsba.order_microservice.domain.repository.ItemRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import mu.KLogger
import org.dataloader.DataLoader
import org.springframework.stereotype.Component
import java.util.*

@Component
class ItemDataLoaderImpl(private val logger: KLogger, private val service: ItemRepository) {

    fun dataLoaderOperationOfItem(userId: UUID): DataLoader<UUID, List<Operation>> {
        logger.warn { "+ItemDataLoaderImpl -> dataLoaderOperationOfItem" }
        return DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future {
                service.myOperations(
                    ids = ids,
                    userId = userId,
                    page = 0,
                    size = 1000
                )
            }
        }
    }

    fun dataLoaderTaskOfItem(userId: UUID): DataLoader<Item, List<Task>> {
        logger.warn { "+ItemDataLoaderImpl -> dataLoaderTaskOfItem" }
        return DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future {
                service.myTasks(
                    ids = ids,
                    userId = userId,
                    page = 0,
                    size = 1000
                )
            }
        }
    }

    fun dataLoaderCategoryInItem(userId: UUID): DataLoader<Item, ItemCategory?> {
        logger.warn { "+ItemDataLoaderImpl -> dataLoaderCategoryInItem" }
        return DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future {
                service.myCategory(
                    ids = ids,
                    userId = userId,
                    page = 0,
                    size = 1000
                )
            }
        }
    }

    fun dataLoaderDetailActor(userId: UUID): DataLoader<DetailItemInOrder, Optional<User>> {
        logger.warn { "+ItemDataLoaderImpl->dataLoaderDetailActor" }
        return DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future {
                service.myDetailActor(
                    ids = ids,
                    userId = userId,
                    page = 0,
                    size = 1000
                )
            }
        }
    }

    fun dataLoaderDetailTechnologies(userId: UUID): DataLoader<DetailItemInOrder, List<Technology>> {
        logger.warn { "+ItemDataLoaderImpl->dataLoaderDetailTechnologies" }
        return DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future {
                service.myDetailTechnologies(
                    ids = ids,
                    userId = userId,
                    page = 0,
                    size = 1000
                )
            }
        }
    }

    fun dataLoaderItemAndItem(userId: UUID): DataLoader<Item, List<Item>> {
        logger.warn { "+dataLoaderItemAndItem" }
        return DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future { service.myItems(ids = ids, userId = userId) }
        }
    }

}