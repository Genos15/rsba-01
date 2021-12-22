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
        const val LOADER_FACTORY_WORKCENTER_OF_TASK = "WORKCENTER_OF_TASK"
        const val LOADER_FACTORY_USERS_OF_TASK = "USERS_OF_TASK"
    }

    fun create(instanceId: UUID): DataLoaderRegistry {
        val registry = DataLoaderRegistry()
        registry.register(LOADER_FACTORY_ORDER_OF_TASK, task.orderLoader(userId = instanceId))
        registry.register(LOADER_FACTORY_ITEM_OF_TASK, task.itemLoader(userId = instanceId))
        registry.register(LOADER_FACTORY_OPERATION_OF_TASK, task.operationLoader(userId = instanceId))
        registry.register(LOADER_FACTORY_WORKCENTER_OF_TASK, task.workcenterLoader(userId = instanceId))
        registry.register(LOADER_FACTORY_USERS_OF_TASK, task.usersLoader(userId = instanceId))
        return registry
    }

}

