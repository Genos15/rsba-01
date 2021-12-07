package com.rsba.order_microservice.resolver.suite

import com.rsba.order_microservice.data.context.dataloader.DataLoaderRegistryFactory
import com.rsba.order_microservice.domain.model.*
import graphql.kickstart.tools.GraphQLResolver
import graphql.relay.Connection
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture

@Component
class OrderGlobalSearchResolver : GraphQLResolver<OrderSearchInstance> {

    fun items(input: OrderSearchInstance, env: DataFetchingEnvironment): CompletableFuture<Connection<Item>> {
        val dataLoader =
            env.getDataLoader<OrderSearchInputValue, Connection<Item>>(DataLoaderRegistryFactory.LOADER_FACTORY_GLOBAL_SEARCH_ITEMS)
        return dataLoader?.load(input.input) ?: CompletableFuture.completedFuture(input.items)
    }

    fun tasks(input: OrderSearchInstance, env: DataFetchingEnvironment): CompletableFuture<Connection<Task>> {
        val dataLoader =
            env.getDataLoader<OrderSearchInputValue, Connection<Task>>(DataLoaderRegistryFactory.LOADER_FACTORY_GLOBAL_SEARCH_TASKS)
        return dataLoader?.load(input.input) ?: CompletableFuture.completedFuture(input.tasks)
    }

    fun technologies(
        input: OrderSearchInstance,
        env: DataFetchingEnvironment
    ): CompletableFuture<Connection<Technology>> {
        val dataLoader =
            env.getDataLoader<OrderSearchInputValue, Connection<Technology>>(DataLoaderRegistryFactory.LOADER_FACTORY_GLOBAL_SEARCH_TECHNOLOGIES)
        return dataLoader?.load(input.input) ?: CompletableFuture.completedFuture(input.technologies)
    }

    fun parameters(input: OrderSearchInstance, env: DataFetchingEnvironment): CompletableFuture<Connection<Parameter>> {
        val dataLoader =
            env.getDataLoader<OrderSearchInputValue, Connection<Parameter>>(DataLoaderRegistryFactory.LOADER_FACTORY_GLOBAL_SEARCH_PARAMETERS)
        return dataLoader?.load(input.input) ?: CompletableFuture.completedFuture(input.parameters)
    }
}