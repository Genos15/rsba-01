package com.rsba.tasks_microservice.data.context.dataloader

import org.dataloader.*
import org.springframework.stereotype.Component
import java.util.*

@Component
class DataLoaderRegistryFactory(
    private val task: TaskDataLoaderImpl,
    private val taskset: TaskSetDataLoaderImpl,
    private val comment: CommentDataLoaderImpl,
) {

    companion object {
        const val LOADER_FACTORY_ORDER_OF_TASK = "ORDER_OF_TASK"
        const val LOADER_FACTORY_ITEM_OF_TASK = "ITEM_OF_TASK"
        const val LOADER_FACTORY_OPERATION_OF_TASK = "OPERATION_OF_TASK"
        const val LOADER_FACTORY_WORKCENTER_OF_TASK = "WORKCENTER_OF_TASK"
        const val LOADER_FACTORY_USERS_OF_TASK = "USERS_OF_TASK"
        const val LOADER_FACTORY_COMMENTS_OF_TASK = "COMMENTS_OF_TASK"
        const val LOADER_FACTORY_TECHNOLOGIES_OF_TASK = "TECHNOLOGIES_OF_TASK"
        const val LOADER_FACTORY_WORKLOGS_OF_TASK = "_WORKLOGS_OF_TASK"


        const val LOADER_FACTORY_USERS_OF_TASKSET = "USERS_OF_TASKSET"
        const val LOADER_FACTORY_TASKS_OF_TASKSET = "TASKS_OF_TASKSET"
        const val LOADER_FACTORY_COMMENTS_OF_TASKSET = "COMMENTS_OF_TASKSET"


        const val LOADER_FACTORY_USER_OF_COMMENT = "USER_OF_COMMENT"
    }

    fun create(instanceId: UUID): DataLoaderRegistry {
        val registry = DataLoaderRegistry()

        registry.register(LOADER_FACTORY_ORDER_OF_TASK, task.orderLoader(userId = instanceId))
        registry.register(LOADER_FACTORY_ITEM_OF_TASK, task.itemLoader(userId = instanceId))
        registry.register(LOADER_FACTORY_OPERATION_OF_TASK, task.operationLoader(userId = instanceId))
        registry.register(LOADER_FACTORY_WORKCENTER_OF_TASK, task.workcenterLoader(userId = instanceId))
        registry.register(LOADER_FACTORY_USERS_OF_TASK, task.usersLoader(userId = instanceId))
        registry.register(LOADER_FACTORY_COMMENTS_OF_TASK, task.commentsLoader(userId = instanceId))
        registry.register(LOADER_FACTORY_TECHNOLOGIES_OF_TASK, task.technologiesLoader(userId = instanceId))
        registry.register(LOADER_FACTORY_WORKLOGS_OF_TASK, task.worklogsLoader(userId = instanceId))


        registry.register(LOADER_FACTORY_USERS_OF_TASKSET, taskset.usersLoader(userId = instanceId))
        registry.register(LOADER_FACTORY_TASKS_OF_TASKSET, taskset.tasksLoader(userId = instanceId))
        registry.register(LOADER_FACTORY_COMMENTS_OF_TASKSET, taskset.commentsLoader(userId = instanceId))


        registry.register(LOADER_FACTORY_USER_OF_COMMENT, comment.userLoader(userId = instanceId))

        return registry
    }

}

