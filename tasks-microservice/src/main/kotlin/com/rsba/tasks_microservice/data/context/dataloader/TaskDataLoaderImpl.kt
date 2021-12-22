package com.rsba.tasks_microservice.data.context.dataloader

import com.rsba.tasks_microservice.domain.model.*
import com.rsba.tasks_microservice.domain.repository.TaskRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import org.dataloader.DataLoader
import org.springframework.stereotype.Component
import java.util.*

@Component
class TaskDataLoaderImpl(private val service: TaskRepository) {

    fun orderLoader(userId: UUID): DataLoader<UUID, Optional<Order>> {
        return DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future { service.order(ids = ids) }
        }
    }

    fun itemLoader(userId: UUID): DataLoader<UUID, Optional<Item>> {
        return DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future { service.item(ids = ids) }
        }
    }

    fun operationLoader(userId: UUID): DataLoader<UUID, Optional<Operation>> {
        return DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future { service.operation(ids = ids) }
        }
    }

    fun workcenterLoader(userId: UUID): DataLoader<UUID, Optional<Workcenter>> {
        return DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future { service.workcenter(ids = ids) }
        }
    }

    fun usersLoader(userId: UUID): DataLoader<UUID, List<User>> =
        DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future {
                service.users(ids = ids)
            }
        }

}