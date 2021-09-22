package com.rsba.order_microservice.resolver.suite

import com.rsba.order_microservice.context.dataloader.DataLoaderRegistryFactory
import com.rsba.order_microservice.domain.model.*
import graphql.kickstart.tools.GraphQLResolver
import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.CompletableFuture

@Component
class TaskResolver(private val logger: KLogger) : GraphQLResolver<Task> {

    fun operation(instance: Task, env: DataFetchingEnvironment): CompletableFuture<Operation>? {
        logger.warn { "+TaskResolver -> operation" }
        val dataLoader =
            env.getDataLoader<UUID, Operation>(DataLoaderRegistryFactory.OPERATION_IN_TASK_DATALOADER)
        return dataLoader?.load(instance.id) ?: CompletableFuture.completedFuture(null)
    }

    fun users(instance: Task, env: DataFetchingEnvironment): CompletableFuture<List<User>>? {
        logger.warn { "+TaskResolver -> users" }
        val dataLoader =
            env.getDataLoader<UUID, List<User>>(DataLoaderRegistryFactory.USER_IN_TASK_DATALOADER)
        return dataLoader?.load(instance.id) ?: CompletableFuture.completedFuture(null)
    }

    fun departments(instance: Task, env: DataFetchingEnvironment): CompletableFuture<List<Department>>? {
        logger.warn { "+TaskResolver -> departments" }
        val dataLoader =
            env.getDataLoader<UUID, List<Department>>(DataLoaderRegistryFactory.DEPARTMENTS_IN_TASK_DATALOADER)
        return dataLoader?.load(instance.id) ?: CompletableFuture.completedFuture(null)
    }

    fun comments(order: Task, env: DataFetchingEnvironment): CompletableFuture<List<Comment>>? {
        logger.warn { "+TaskResolver -> comments" }
        val dataLoader =
            env.getDataLoader<UUID, List<Comment>>(DataLoaderRegistryFactory.COMMENTS_IN_TASK_DATALOADER)
        return dataLoader?.load(order.id) ?: CompletableFuture.completedFuture(null)
    }

    fun item(instance: Task, env: DataFetchingEnvironment): CompletableFuture<Item>? {
        logger.warn { "+TaskResolver -> item" }
        val dataLoader =
            env.getDataLoader<UUID, Item>(DataLoaderRegistryFactory.ITEM_IN_TASK_DATALOADER)
        return dataLoader?.load(instance.id) ?: CompletableFuture.completedFuture(null)
    }

    fun order(instance: Task, env: DataFetchingEnvironment): CompletableFuture<Order>? {
        logger.warn { "+TaskResolver -> order" }
        val dataLoader =
            env.getDataLoader<UUID, Order>(DataLoaderRegistryFactory.ORDER_IN_TASK_DATALOADER)
        return dataLoader?.load(instance.id) ?: CompletableFuture.completedFuture(null)
    }
}