package com.rsba.tasks_microservice.resolver.suite

import com.rsba.tasks_microservice.data.context.dataloader.DataLoaderRegistryFactory
import com.rsba.tasks_microservice.domain.model.*
import graphql.kickstart.tools.GraphQLResolver
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import java.util.*

import java.util.concurrent.CompletableFuture

@Component
class CommentResolver : GraphQLResolver<Comment> {

    fun reporter(input: Comment, env: DataFetchingEnvironment): CompletableFuture<Optional<User>> {
        val dataLoader =
            env.getDataLoader<UUID, Optional<User>>(DataLoaderRegistryFactory.LOADER_FACTORY_USER_OF_COMMENT)
        return dataLoader?.load(input.id) ?: CompletableFuture.completedFuture(Optional.empty())
    }

}