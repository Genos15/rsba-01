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
class ItemResolver(private val logger: KLogger) : GraphQLResolver<Item> {

    fun operations(instance: Item, env: DataFetchingEnvironment): CompletableFuture<List<Operation>>? {
        logger.warn { "+ItemResolver -> operations" }
        val dataLoader =
            env.getDataLoader<UUID, List<Operation>>(DataLoaderRegistryFactory.OPERATIONS_IN_ITEM)
        return dataLoader?.load(instance.id) ?: CompletableFuture.completedFuture(emptyList())
    }

    fun tasks(instance: Item, env: DataFetchingEnvironment): CompletableFuture<List<Task>>? {
        logger.warn { "+ItemResolver -> tasks" }
        val dataLoader =
            env.getDataLoader<Item, List<Task>>(DataLoaderRegistryFactory.TASK_IN_ITEM_DATALOADER)
        return dataLoader?.load(instance) ?: CompletableFuture.completedFuture(emptyList())
    }

    fun category(instance: Item, env: DataFetchingEnvironment): CompletableFuture<CategoryOfItem?>? {
        logger.warn { "+ItemResolver->category" }
        val dataLoader =
            env.getDataLoader<Item, CategoryOfItem?>(DataLoaderRegistryFactory.CATEGORY_IN_ITEM)
        return dataLoader?.load(instance) ?: CompletableFuture.completedFuture(null)
    }

}