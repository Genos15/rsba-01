package com.rsba.component_microservice.resolver.suite

import com.rsba.component_microservice.data.context.dataloader.DataLoaderRegistryFactory
import com.rsba.component_microservice.domain.model.Item
import com.rsba.component_microservice.domain.model.ItemCategory
import graphql.kickstart.tools.GraphQLResolver
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.CompletableFuture

@Component
class ItemCategoryResolver : GraphQLResolver<ItemCategory> {

    fun children(item: ItemCategory, env: DataFetchingEnvironment): CompletableFuture<List<ItemCategory>> {
        val dataLoader =
            env.getDataLoader<UUID, List<ItemCategory>>(DataLoaderRegistryFactory.SUB_ITEM_CATEGORY_DATALOADER)
        return dataLoader?.load(item.id) ?: CompletableFuture.completedFuture(emptyList())
    }


    fun subItems(item: ItemCategory, env: DataFetchingEnvironment): CompletableFuture<List<Item>> {
        val dataLoader =
            env.getDataLoader<UUID, List<Item>>(DataLoaderRegistryFactory.SUB_ITEMS_IN_ITEM_CATEGORY_DATALOADER)
        return dataLoader?.load(item.id) ?: CompletableFuture.completedFuture(emptyList())
    }

}