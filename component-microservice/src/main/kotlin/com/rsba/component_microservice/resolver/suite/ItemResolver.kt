package com.rsba.component_microservice.resolver.suite

import com.rsba.component_microservice.data.context.dataloader.DataLoaderRegistryFactory
import com.rsba.component_microservice.domain.model.ItemCategory
import com.rsba.component_microservice.domain.model.Item
import com.rsba.component_microservice.domain.model.Operation
import graphql.kickstart.tools.GraphQLResolver
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.CompletableFuture

@Component
class ItemResolver : GraphQLResolver<Item> {

    fun operations(item: Item, env: DataFetchingEnvironment): CompletableFuture<List<Operation>> {
        val dataLoader =
            env.getDataLoader<UUID, List<Operation>>(DataLoaderRegistryFactory.OPERATION_IN_ITEM_DATALOADER)
        return dataLoader?.load(item.id) ?: CompletableFuture.completedFuture(emptyList())
    }

    fun category(item: Item, env: DataFetchingEnvironment): CompletableFuture<Optional<ItemCategory>> {
        val dataLoader =
            env.getDataLoader<UUID, Optional<ItemCategory>>(DataLoaderRegistryFactory.ITEMS_CATEGORY_DATALOADER)
        return dataLoader?.load(item.id) ?: CompletableFuture.completedFuture(Optional.empty())
    }

    fun components(item: Item, env: DataFetchingEnvironment): CompletableFuture<List<Item>> {
        val dataLoader =
            env.getDataLoader<UUID, List<Item>>(DataLoaderRegistryFactory.ITEMS_COMPONENTS_DATALOADER)
        return dataLoader?.load(item.id) ?: CompletableFuture.completedFuture(emptyList())
    }

}