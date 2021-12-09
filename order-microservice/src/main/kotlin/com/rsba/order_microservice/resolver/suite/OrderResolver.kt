package com.rsba.order_microservice.resolver.suite

import com.rsba.order_microservice.data.context.dataloader.DataLoaderRegistryFactory
import com.rsba.order_microservice.domain.model.*
import graphql.kickstart.tools.GraphQLResolver
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.CompletableFuture

@Component
class OrderResolver : GraphQLResolver<Order> {

    fun customer(order: Order, env: DataFetchingEnvironment): CompletableFuture<Optional<Customer>> {
        val dataLoader =
            env.getDataLoader<UUID, Optional<Customer>>(DataLoaderRegistryFactory.LOADER_FACTORY_CUSTOMER_OF_ORDER)
        return dataLoader?.load(order.id) ?: CompletableFuture.completedFuture(Optional.empty())
    }

    fun manager(order: Order, env: DataFetchingEnvironment): CompletableFuture<Optional<Agent>> {
        val dataLoader =
            env.getDataLoader<UUID, Optional<Agent>>(DataLoaderRegistryFactory.LOADER_FACTORY_MANAGER_OF_ORDER)
        return dataLoader?.load(order.id) ?: CompletableFuture.completedFuture(Optional.empty())
    }

    fun agent(order: Order, env: DataFetchingEnvironment): CompletableFuture<Optional<Agent>> {
        val dataLoader =
            env.getDataLoader<UUID, Optional<Agent>>(DataLoaderRegistryFactory.LOADER_FACTORY_AGENT_OF_ORDER)
        return dataLoader?.load(order.id) ?: CompletableFuture.completedFuture(Optional.empty())
    }

    fun type(order: Order, env: DataFetchingEnvironment): CompletableFuture<Optional<OrderType>> {
        val dataLoader =
            env.getDataLoader<UUID, Optional<OrderType>>(DataLoaderRegistryFactory.LOADER_FACTORY_TYPE_OF_ORDER)
        return dataLoader?.load(order.id) ?: CompletableFuture.completedFuture(Optional.empty())
    }

    fun items(order: Order, env: DataFetchingEnvironment): CompletableFuture<List<Item>> {
        val dataLoader =
            env.getDataLoader<UUID, List<Item>>(DataLoaderRegistryFactory.LOADER_FACTORY_ITEMS_OF_ORDER)
        return dataLoader?.load(order.id) ?: CompletableFuture.completedFuture(emptyList())
    }

    fun tasks(order: Order, env: DataFetchingEnvironment): CompletableFuture<List<Task>> {
        val dataLoader =
            env.getDataLoader<UUID, List<Task>>(DataLoaderRegistryFactory.LOADER_FACTORY_TASKS_OF_ORDER)
        return dataLoader?.load(order.id) ?: CompletableFuture.completedFuture(emptyList())
    }

    fun technologies(order: Order, env: DataFetchingEnvironment): CompletableFuture<List<Technology>> {
        val dataLoader =
            env.getDataLoader<UUID, List<Technology>>(DataLoaderRegistryFactory.LOADER_FACTORY_TECHNOLOGIES_OF_ORDER)
        return dataLoader?.load(order.id) ?: CompletableFuture.completedFuture(emptyList())
    }

    fun parameters(order: Order, env: DataFetchingEnvironment): CompletableFuture<List<Parameter>> {
        val dataLoader =
            env.getDataLoader<UUID, List<Parameter>>(DataLoaderRegistryFactory.LOADER_FACTORY_PARAMETERS_OF_ORDER)
        return dataLoader?.load(order.id) ?: CompletableFuture.completedFuture(emptyList())
    }

    fun categories(order: Order, env: DataFetchingEnvironment): CompletableFuture<List<ItemCategory>> {
        val dataLoader =
            env.getDataLoader<UUID, List<ItemCategory>>(DataLoaderRegistryFactory.LOADER_FACTORY_CATEGORIES_OF_ORDER)
        return dataLoader?.load(order.id) ?: CompletableFuture.completedFuture(emptyList())
    }

    fun worklogs(order: Order, env: DataFetchingEnvironment): CompletableFuture<List<Worklog>> {
        val dataLoader =
            env.getDataLoader<UUID, List<Worklog>>(DataLoaderRegistryFactory.LOADER_FACTORY_WORKLOGS_OF_ORDER)
        return dataLoader?.load(order.id) ?: CompletableFuture.completedFuture(emptyList())
    }

    fun statistics(instance: Order, env: DataFetchingEnvironment): CompletableFuture<Optional<OrderStatistics>> {
        val dataLoader =
            env.getDataLoader<UUID, Optional<OrderStatistics>>(DataLoaderRegistryFactory.LOADER_FACTORY_STATISTICS_OF_ORDER)
        return dataLoader?.load(instance.id) ?: CompletableFuture.completedFuture(Optional.empty())
    }


}