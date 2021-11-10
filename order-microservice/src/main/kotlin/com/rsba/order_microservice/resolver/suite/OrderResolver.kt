package com.rsba.order_microservice.resolver.suite

import com.rsba.order_microservice.data.context.dataloader.DataLoaderRegistryFactory
import com.rsba.order_microservice.domain.model.*
import graphql.kickstart.tools.GraphQLResolver
import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.CompletableFuture

@Component
class OrderResolver(
    private val logger: KLogger,
) : GraphQLResolver<Order> {

    fun customer(order: Order, env: DataFetchingEnvironment): CompletableFuture<Customer?>? {
        logger.warn { "+OrderResolver -> customer" }
        val dataLoader = env.getDataLoader<UUID, Customer>(DataLoaderRegistryFactory.CUSTOMER_OF_ORDER)
        return dataLoader?.load(order.id) ?: CompletableFuture.completedFuture(null)
    }

    fun agent(order: Order, env: DataFetchingEnvironment): CompletableFuture<Agent?>? {
        logger.warn { "+OrderResolver -> agent" }
        val dataLoader = env.getDataLoader<UUID, Agent>(DataLoaderRegistryFactory.AGENT_OF_ORDER)
        return dataLoader?.load(order.id) ?: CompletableFuture.completedFuture(null)
    }

    fun manager(order: Order, env: DataFetchingEnvironment): CompletableFuture<Agent?>? {
        logger.warn { "+OrderResolver -> manager" }
        val dataLoader = env.getDataLoader<UUID, Agent>(DataLoaderRegistryFactory.MANAGER_OF_ORDER)
        return dataLoader?.load(order.id) ?: CompletableFuture.completedFuture(null)
    }

    fun items(order: Order, env: DataFetchingEnvironment): CompletableFuture<List<Item>>? {
        logger.warn { "+OrderResolver -> items" }
        val dataLoader =
            env.getDataLoader<UUID, List<Item>>(DataLoaderRegistryFactory.ITEM_IN_ORDER)
        return dataLoader?.load(order.id) ?: CompletableFuture.completedFuture(listOf())
    }

    fun type(order: Order, env: DataFetchingEnvironment): CompletableFuture<Optional<OrderType>> {
        logger.warn { "+OrderResolver -> type" }
        val dataLoader =
            env.getDataLoader<UUID, Optional<OrderType>>(DataLoaderRegistryFactory.TYPE_OF_ORDER_DATALOADER)
        return dataLoader?.load(order.id) ?: CompletableFuture.completedFuture(Optional.empty())
    }

}