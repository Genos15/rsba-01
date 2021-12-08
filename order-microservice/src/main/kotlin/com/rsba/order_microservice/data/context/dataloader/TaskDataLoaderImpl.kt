package com.rsba.order_microservice.data.context.dataloader

import com.rsba.order_microservice.domain.model.*
import com.rsba.order_microservice.domain.repository.TaskRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import org.dataloader.DataLoader
import org.springframework.stereotype.Component
import java.util.*

@Component
class TaskDataLoaderImpl(private val service: TaskRepository) {

    fun operationLoader(userId: UUID): DataLoader<UUID, Optional<Operation>> =
        DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future {
                service.operation(ids = ids)
            }
        }

    fun itemLoader(userId: UUID): DataLoader<UUID, Optional<Item>> =
        DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future {
                service.item(ids = ids)
            }
        }

    fun orderLoader(userId: UUID): DataLoader<UUID, Optional<Order>> =
        DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future {
                service.order(ids = ids)
            }
        }

    fun departmentsLoader(userId: UUID): DataLoader<UUID, List<Group>> =
        DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future {
                service.departments(ids = ids)
            }
        }

//    fun dataLoaderOperationInTask(userId: UUID): DataLoader<UUID, Operation> {
//        return DataLoader.newMappedDataLoader { ids ->
//            GlobalScope.future {
//                service.myOperation(
//                    ids = ids,
//                    userId = userId,
//                    first = 0,
//                    after = null
//                )
//            }
//        }
//    }
//
//    fun dataLoaderDepartmentsInTask(userId: UUID): DataLoader<UUID, List<Department>> {
//        return DataLoader.newMappedDataLoader { ids ->
//            GlobalScope.future {
//                service.myDepartments(
//                    ids = ids,
//                    userId = userId,
//                    first = 0,
//                    after = null
//                )
//            }
//        }
//    }
//
//    fun dataLoaderCommentsInTask(userId: UUID): DataLoader<UUID, List<Comment>> {
//        return DataLoader.newMappedDataLoader { ids ->
//            GlobalScope.future {
//                service.myComments(
//                    ids = ids,
//                    userId = userId,
//                    first = 0,
//                    after = null
//                )
//            }
//        }
//    }
//
//    fun dataLoaderUsersInTask(userId: UUID): DataLoader<UUID, List<User>> {
//        return DataLoader.newMappedDataLoader { ids ->
//            GlobalScope.future {
//                service.usersInTask(
//                    ids = ids,
//                    userId = userId,
//                    first = 0,
//                    after = UUID.randomUUID()
//                )
//            }
//        }
//    }
//
//    fun dataLoaderItemInTask(userId: UUID): DataLoader<UUID, Item> {
//        return DataLoader.newMappedDataLoader { ids ->
//            GlobalScope.future {
//                service.myItem(
//                    ids = ids,
//                    userId = userId,
//                    first = 0,
//                    after = null
//                )
//            }
//        }
//    }
//
//    fun dataLoaderOrderInTask(userId: UUID): DataLoader<UUID, Order> {
//        return DataLoader.newMappedDataLoader { ids ->
//            GlobalScope.future {
//                service.myOrder(
//                    ids = ids,
//                    userId = userId,
//                    first = 0,
//                    after = null
//                )
//            }
//        }
//    }

}