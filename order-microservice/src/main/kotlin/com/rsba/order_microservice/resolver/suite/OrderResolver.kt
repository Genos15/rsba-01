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
class OrderResolver(
    private val logger: KLogger,
) : GraphQLResolver<Order> {

    fun customer(order: Order, env: DataFetchingEnvironment): CompletableFuture<Customer?>? {
        logger.warn { "+---- OrderResolver -> customer" }
        val dataLoader = env.getDataLoader<UUID, Customer>(DataLoaderRegistryFactory.CUSTOMER_OF_ORDER)
        return dataLoader?.load(order.id) ?: CompletableFuture.completedFuture(null)
    }

    fun agent(order: Order, env: DataFetchingEnvironment): CompletableFuture<Agent?>? {
        logger.warn { "+---- OrderResolver -> agent" }
        val dataLoader = env.getDataLoader<UUID, Agent>(DataLoaderRegistryFactory.AGENT_OF_ORDER)
        return dataLoader?.load(order.id) ?: CompletableFuture.completedFuture(null)
    }

    fun manager(order: Order, env: DataFetchingEnvironment): CompletableFuture<Agent?>? {
        logger.warn { "+OrderResolver -> manager" }
        val dataLoader = env.getDataLoader<UUID, Agent>(DataLoaderRegistryFactory.MANAGER_OF_ORDER)
        return dataLoader?.load(order.id) ?: CompletableFuture.completedFuture(null)
    }

//    fun categories(order: Order, env: DataFetchingEnvironment): CompletableFuture<List<CategoryOfItemInOrder>>? {
//        logger.warn { "+---- OrderResolver -> categories" }
//        val dataLoader =
//            env.getDataLoader<UUID, List<CategoryOfItemInOrder>>(DataLoaderRegistryFactory.CATEGORY_OF_ITEM_IN_ORDER)
//        return dataLoader?.load(order.id) ?: CompletableFuture.completedFuture(listOf())
//    }


//    fun categories(order: Order, env: DataFetchingEnvironment): CompletableFuture<List<CategoryOfItem>>? {
//        logger.warn { "+OrderResolver -> categories" }
//        val dataLoader =
//            env.getDataLoader<UUID, List<CategoryOfItem>>(DataLoaderRegistryFactory.CATEGORY_OF_ITEM_IN_ORDER_POST_MVP)
//        return dataLoader?.load(order.id) ?: CompletableFuture.completedFuture(listOf())
//    }


    fun items(order: Order, env: DataFetchingEnvironment): CompletableFuture<List<Item>>? {
        logger.warn { "+OrderResolver -> items" }
        val dataLoader =
            env.getDataLoader<UUID, List<Item>>(DataLoaderRegistryFactory.ITEM_IN_ORDER)
        return dataLoader?.load(order.id) ?: CompletableFuture.completedFuture(listOf())
    }

}