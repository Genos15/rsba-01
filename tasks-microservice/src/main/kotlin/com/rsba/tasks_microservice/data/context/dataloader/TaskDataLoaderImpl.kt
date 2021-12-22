package com.rsba.tasks_microservice.data.context.dataloader

import com.rsba.tasks_microservice.domain.model.Item
import com.rsba.tasks_microservice.domain.model.Operation
import com.rsba.tasks_microservice.domain.model.Order
import com.rsba.tasks_microservice.domain.model.Workcenter
import com.rsba.tasks_microservice.domain.repository.TaskRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import org.dataloader.DataLoader
import org.springframework.stereotype.Component
import java.util.*

@Component
class TaskDataLoaderImpl(private val service: TaskRepository) {

    fun dataLoaderOrder(userId: UUID): DataLoader<UUID, Optional<Order>> {
        return DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future { service.order(ids = ids) }
        }
    }

    fun dataLoaderItem(userId: UUID): DataLoader<UUID, Optional<Item>> {
        return DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future { service.item(ids = ids) }
        }
    }

    fun dataLoaderOperation(userId: UUID): DataLoader<UUID, Optional<Operation>> {
        return DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future { service.operation(ids = ids) }
        }
    }

    fun dataLoaderWorkcenter(userId: UUID): DataLoader<UUID, Optional<Workcenter>> {
        return DataLoader.newMappedDataLoader { ids ->
            GlobalScope.future { service.workcenter(ids = ids) }
        }
    }

}