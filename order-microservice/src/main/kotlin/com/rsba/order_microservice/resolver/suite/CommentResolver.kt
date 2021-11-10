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
class CommentResolver(private val logger: KLogger) : GraphQLResolver<Comment> {

    fun actor(order: Comment, env: DataFetchingEnvironment): CompletableFuture<Optional<User>>? {
        logger.warn { "+CommentResolver -> actor" }
        val dataLoader =
            env.getDataLoader<UUID, Optional<User>>(DataLoaderRegistryFactory.ACTOR_OF_COMMENT_DATALOADER)
        return dataLoader?.load(order.id) ?: CompletableFuture.completedFuture(Optional.empty())
    }

}