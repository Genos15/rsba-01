package com.rsba.order_microservice.resolver.suite

import com.rsba.order_microservice.data.context.dataloader.DataLoaderRegistryFactory
import com.rsba.order_microservice.domain.model.*
import graphql.kickstart.tools.GraphQLResolver
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.CompletableFuture

@Component
class ItemStatisticsResolver : GraphQLResolver<ItemStatistics> {

    fun technologies(instance: ItemStatistics, env: DataFetchingEnvironment): CompletableFuture<List<Technology>> {
        val dataLoader =
            env.getDataLoader<UUID, List<Technology>>(DataLoaderRegistryFactory.LOADER_FACTORY_TECHNOLOGIES_OF_ITEM)
        return dataLoader?.load(instance.id) ?: CompletableFuture.completedFuture(emptyList())
    }

    fun whoAdded(
        instance: ItemStatistics,
        env: DataFetchingEnvironment
    ): CompletableFuture<Optional<User>> {
        val dataLoader =
            env.getDataLoader<UUID, Optional<User>>(DataLoaderRegistryFactory.LOADER_FACTORY_WHO_ADDED_OF_ITEM)
        return dataLoader?.load(instance.id) ?: CompletableFuture.completedFuture(Optional.empty())
    }
}