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
class WorklogResolver(private val logger: KLogger) : GraphQLResolver<Worklog> {
    fun actor(
        instance: Worklog,
        env: DataFetchingEnvironment
    ): CompletableFuture<Optional<User>>? {
        logger.warn { "+WorklogResolver -> actor" }
        val dataLoader =
            env.getDataLoader<UUID, Optional<User>>(DataLoaderRegistryFactory.ACTOR_IN_WORKLOG_DATALOADER)
        return dataLoader?.load(instance.id) ?: CompletableFuture.completedFuture(Optional.empty())
    }
}