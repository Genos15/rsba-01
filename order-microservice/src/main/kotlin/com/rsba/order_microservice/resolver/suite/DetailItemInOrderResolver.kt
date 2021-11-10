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
class DetailItemInOrderResolver(private val logger: KLogger) : GraphQLResolver<DetailItemInOrder> {

    fun whoAdded(instance: DetailItemInOrder, env: DataFetchingEnvironment): CompletableFuture<Optional<User>>? {
        logger.warn { "+DetailItemInOrderResolver->whoAdded" }
        val dataLoader =
            env.getDataLoader<DetailItemInOrder, Optional<User>>(DataLoaderRegistryFactory.ACTOR_IN_DETAIL_OF_ORDER_DATALOADER)
        return dataLoader?.load(instance) ?: CompletableFuture.completedFuture(Optional.empty())
    }

    fun technologies(instance: DetailItemInOrder, env: DataFetchingEnvironment): CompletableFuture<List<Technology>>? {
        logger.warn { "+DetailItemInOrderResolver->technologies" }
        val dataLoader =
            env.getDataLoader<DetailItemInOrder, List<Technology>>(DataLoaderRegistryFactory.TECHNOLOGIES_IN_DETAIL_OF_ORDER_DATALOADER)
        return dataLoader?.load(instance) ?: CompletableFuture.completedFuture(emptyList())
    }

}