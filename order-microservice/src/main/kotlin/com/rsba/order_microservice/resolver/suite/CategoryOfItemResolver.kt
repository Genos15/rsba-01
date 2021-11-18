package com.rsba.order_microservice.resolver.suite

import com.rsba.order_microservice.data.context.dataloader.DataLoaderRegistryFactory
import com.rsba.order_microservice.domain.model.*
import graphql.kickstart.tools.GraphQLResolver
import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture

@Component
class CategoryOfItemResolver(private val logger: KLogger) : GraphQLResolver<ItemCategory> {
    fun items(
        module: ItemCategory,
        env: DataFetchingEnvironment
    ): CompletableFuture<List<Item>>? {
        logger.warn { "+CategoryOfItemResolver -> items" }
        val dataLoader =
            env.getDataLoader<ItemCategory, List<Item>>(DataLoaderRegistryFactory.ITEM_IN_ORDER)
        return dataLoader?.load(module) ?: CompletableFuture.completedFuture(mutableListOf())
    }
}