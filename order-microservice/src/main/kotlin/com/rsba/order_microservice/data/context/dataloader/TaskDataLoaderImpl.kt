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

    fun parametersLoader(userId: UUID): DataLoader<UUID, List<Parameter>> =
        DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future {
                service.parameters(ids = ids)
            }
        }

}