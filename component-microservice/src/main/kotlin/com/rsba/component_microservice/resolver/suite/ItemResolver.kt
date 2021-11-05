package com.rsba.component_microservice.resolver.suite

import com.rsba.component_microservice.data.context.dataloader.DataLoaderRegistryFactory
import com.rsba.component_microservice.domain.model.ItemCategory
import com.rsba.component_microservice.domain.model.Item
import com.rsba.component_microservice.domain.model.Operation
import graphql.kickstart.tools.GraphQLResolver
import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.CompletableFuture

@Component
class ItemResolver(private val logger: KLogger) : GraphQLResolver<Item> {

    fun operations(item: Item, env: DataFetchingEnvironment): CompletableFuture<List<Operation>?>? {
        logger.warn { "+ItemResolver -> operations" }
        val dataLoader =
            env.getDataLoader<UUID, List<Operation>>(DataLoaderRegistryFactory.OPERATION_IN_ITEM_DATALOADER)
        return dataLoader?.load(item.id) ?: CompletableFuture.completedFuture(null)
    }

    fun category(item: Item, env: DataFetchingEnvironment): CompletableFuture<ItemCategory?>? {
        logger.warn { "+ItemResolver -> category" }
        val dataLoader =
            env.getDataLoader<UUID, ItemCategory>(DataLoaderRegistryFactory.CATEGORY_OF_ITEM_IN_ITEM_DATALOADER)
        return dataLoader?.load(item.id) ?: CompletableFuture.completedFuture(null)
    }

}