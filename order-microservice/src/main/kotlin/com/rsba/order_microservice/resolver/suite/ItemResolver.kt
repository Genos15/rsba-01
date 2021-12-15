package com.rsba.order_microservice.resolver.suite

import com.rsba.order_microservice.data.context.dataloader.DataLoaderRegistryFactory
import com.rsba.order_microservice.domain.model.*
import graphql.kickstart.tools.GraphQLResolver
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.CompletableFuture

@Component
class ItemResolver : GraphQLResolver<Item> {

    fun operations(instance: Item, env: DataFetchingEnvironment): CompletableFuture<List<Operation>> {
        val dataLoader =
            env.getDataLoader<UUID, List<Operation>>(DataLoaderRegistryFactory.OPERATIONS_IN_ITEM)
        return dataLoader?.load(instance.id) ?: CompletableFuture.completedFuture(emptyList())
    }

    fun tasks(instance: Item, env: DataFetchingEnvironment): CompletableFuture<List<Task>> {
        val dataLoader =
            env.getDataLoader<Item, List<Task>>(DataLoaderRegistryFactory.TASK_IN_ITEM_DATALOADER)
        return dataLoader?.load(instance) ?: CompletableFuture.completedFuture(emptyList())
    }

    fun category(instance: Item, env: DataFetchingEnvironment): CompletableFuture<ItemCategory?>? {
        val dataLoader =
            env.getDataLoader<Item, ItemCategory?>(DataLoaderRegistryFactory.CATEGORY_IN_ITEM)
        return dataLoader?.load(instance) ?: CompletableFuture.completedFuture(null)
    }

    fun components(instance: Item, env: DataFetchingEnvironment): CompletableFuture<List<Item>> {
        val dataLoader = env.getDataLoader<Item, List<Item>>(DataLoaderRegistryFactory.ITEM_IN_ITEM_DATALOADER)
        return dataLoader?.load(instance) ?: CompletableFuture.completedFuture(emptyList())
    }

    fun statistics(instance: Item, env: DataFetchingEnvironment): CompletableFuture<Optional<ItemStatistics>> {
        val dataLoader =
            env.getDataLoader<UUID, Optional<ItemStatistics>>(DataLoaderRegistryFactory.LOADER_FACTORY_STATISTICS_OF_ITEM)
        return dataLoader?.load(instance.id) ?: CompletableFuture.completedFuture(Optional.empty())
    }

    fun parameters(instance: Item, env: DataFetchingEnvironment): CompletableFuture<List<Parameter>> {
        val dataLoader =
            env.getDataLoader<UUID, List<Parameter>>(DataLoaderRegistryFactory.LOADER_FACTORY_PARAMETERS_OF_ITEM)
        return dataLoader?.load(instance.id) ?: CompletableFuture.completedFuture(emptyList())
    }
}