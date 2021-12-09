package com.rsba.order_microservice.resolver.suite

import com.rsba.order_microservice.data.context.dataloader.DataLoaderRegistryFactory
import com.rsba.order_microservice.domain.model.*
import graphql.kickstart.tools.GraphQLResolver
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.CompletableFuture

@Component
class OrderStatisticsResolver : GraphQLResolver<OrderStatistics> {

    fun itemCategory(
        instance: OrderStatistics,
        env: DataFetchingEnvironment
    ): CompletableFuture<List<ItemCategoryStatistics>> {
        val dataLoader =
            env.getDataLoader<UUID, List<ItemCategoryStatistics>>(DataLoaderRegistryFactory.LOADER_FACTORY_ITEM_CATEGORIES_STATISTICS_OF_ORDER)
        return dataLoader?.load(instance.id) ?: CompletableFuture.completedFuture(emptyList())
    }

    fun departments(
        instance: OrderStatistics,
        env: DataFetchingEnvironment
    ): CompletableFuture<List<DepartmentStatistics>> {
        val dataLoader =
            env.getDataLoader<UUID, List<DepartmentStatistics>>(DataLoaderRegistryFactory.LOADER_FACTORY_DEPARTMENTS_STATISTICS_OF_ORDER)
        return dataLoader?.load(instance.id) ?: CompletableFuture.completedFuture(emptyList())
    }
}