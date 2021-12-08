package com.rsba.order_microservice.resolver.suite

import com.rsba.order_microservice.data.context.dataloader.DataLoaderRegistryFactory
import com.rsba.order_microservice.domain.model.*
import graphql.kickstart.tools.GraphQLResolver
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.CompletableFuture

@Component
class TaskResolver : GraphQLResolver<Task> {

    fun operation(instance: Task, env: DataFetchingEnvironment): CompletableFuture<Optional<Operation>> {
        val dataLoader =
            env.getDataLoader<UUID, Optional<Operation>>(DataLoaderRegistryFactory.LOADER_FACTORY_OPERATION_OF_TASK)
        return dataLoader?.load(instance.id) ?: CompletableFuture.completedFuture(Optional.empty())
    }

//    fun users(instance: Task, env: DataFetchingEnvironment): CompletableFuture<List<User>>? {
//        logger.warn { "+TaskResolver -> users" }
//        val dataLoader =
//            env.getDataLoader<UUID, List<User>>(DataLoaderRegistryFactory.USER_IN_TASK_DATALOADER)
//        return dataLoader?.load(instance.id) ?: CompletableFuture.completedFuture(null)
//    }

    fun departments(instance: Task, env: DataFetchingEnvironment): CompletableFuture<List<Department>> {
        val dataLoader =
            env.getDataLoader<UUID, List<Department>>(DataLoaderRegistryFactory.LOADER_FACTORY_DEPARTMENTS_OF_TASK)
        return dataLoader?.load(instance.id) ?: CompletableFuture.completedFuture(emptyList())
    }

//    fun comments(order: Task, env: DataFetchingEnvironment): CompletableFuture<List<Comment>>? {
//        logger.warn { "+TaskResolver -> comments" }
//        val dataLoader =
//            env.getDataLoader<UUID, List<Comment>>(DataLoaderRegistryFactory.COMMENTS_IN_TASK_DATALOADER)
//        return dataLoader?.load(order.id) ?: CompletableFuture.completedFuture(null)
//    }

    fun item(instance: Task, env: DataFetchingEnvironment): CompletableFuture<Optional<Item>> {
        val dataLoader =
            env.getDataLoader<UUID, Optional<Item>>(DataLoaderRegistryFactory.LOADER_FACTORY_ITEM_OF_TASK)
        return dataLoader?.load(instance.id) ?: CompletableFuture.completedFuture(Optional.empty())
    }

    fun order(instance: Task, env: DataFetchingEnvironment): CompletableFuture<Optional<Order>> {
        val dataLoader =
            env.getDataLoader<UUID, Optional<Order>>(DataLoaderRegistryFactory.LOADER_FACTORY_ORDER_OF_TASK)
        return dataLoader?.load(instance.id) ?: CompletableFuture.completedFuture(Optional.empty())
    }
}