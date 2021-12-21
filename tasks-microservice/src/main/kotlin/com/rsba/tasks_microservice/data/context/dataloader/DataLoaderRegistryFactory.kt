package com.rsba.tasks_microservice.data.context.dataloader

import org.dataloader.*
import org.springframework.stereotype.Component
import java.util.*

@Component
class DataLoaderRegistryFactory(private val task: TaskDataLoaderImpl) {

    companion object {
        const val LOADER_FACTORY_ORDER_OF_TASK = "ORDER_OF_TASK"
        const val LOADER_FACTORY_ITEM_OF_TASK = "ITEM_OF_TASK"
        const val LOADER_FACTORY_OPERATION_OF_TASK = "OPERATION_OF_TASK"
    }

    fun create(instanceId: UUID): DataLoaderRegistry {
        val registry = DataLoaderRegistry()
        registry.register(LOADER_FACTORY_ORDER_OF_TASK, task.dataLoaderOrder(userId = instanceId))
        registry.register(LOADER_FACTORY_ITEM_OF_TASK, task.dataLoaderItem(userId = instanceId))
        registry.register(LOADER_FACTORY_OPERATION_OF_TASK, task.dataLoaderOperation(userId = instanceId))
        return registry
    }

}

