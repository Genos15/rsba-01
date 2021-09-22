package com.rsba.order_microservice.context.dataloader

import com.rsba.order_microservice.domain.model.*
import com.rsba.order_microservice.repository.TaskRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import mu.KLogger
import org.dataloader.DataLoader
import org.springframework.stereotype.Component
import java.util.*

@Component
class TaskDataLoaderImpl(private val logger: KLogger, private val service: TaskRepository) {

    fun dataLoaderOperationInTask(userId: UUID): DataLoader<UUID, Operation> {
        logger.warn { "+ItemDataLoaderImpl -> dataLoaderOperationInTask" }
        return DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future {
                service.myOperation(
                    ids = ids,
                    userId = userId,
                    first = 0,
                    after = null
                )
            }
        }
    }

    fun dataLoaderDepartmentsInTask(userId: UUID): DataLoader<UUID, List<Department>> {
        logger.warn { "+ItemDataLoaderImpl -> dataLoaderDepartmentsInTask" }
        return DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future {
                service.myDepartments(
                    ids = ids,
                    userId = userId,
                    first = 0,
                    after = null
                )
            }
        }
    }

    fun dataLoaderCommentsInTask(userId: UUID): DataLoader<UUID, List<Comment>> {
        logger.warn { "+ItemDataLoaderImpl -> dataLoaderCommentsInTask" }
        return DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future {
                service.myComments(
                    ids = ids,
                    userId = userId,
                    first = 0,
                    after = null
                )
            }
        }
    }

    fun dataLoaderUsersInTask(userId: UUID): DataLoader<UUID, List<User>> {
        logger.warn { "+ItemDataLoaderImpl -> dataLoaderUsersInTask" }
        return DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future {
                service.usersInTask(
                    ids = ids,
                    userId = userId,
                    first = 0,
                    after = UUID.randomUUID()
                )
            }
        }
    }

    fun dataLoaderItemInTask(userId: UUID): DataLoader<UUID, Item> {
        logger.warn { "+ItemDataLoaderImpl -> dataLoaderItemInTask" }
        return DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future {
                service.myItem(
                    ids = ids,
                    userId = userId,
                    first = 0,
                    after = null
                )
            }
        }
    }

    fun dataLoaderOrderInTask(userId: UUID): DataLoader<UUID, Order> {
        logger.warn { "+ItemDataLoaderImpl -> dataLoaderOrderInTask" }
        return DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future {
                service.myOrder(
                    ids = ids,
                    userId = userId,
                    first = 0,
                    after = null
                )
            }
        }
    }

}