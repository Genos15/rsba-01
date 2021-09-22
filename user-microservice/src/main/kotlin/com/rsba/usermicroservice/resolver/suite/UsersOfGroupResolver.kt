package com.rsba.usermicroservice.resolver.suite

import com.rsba.usermicroservice.context.dataloader.DataLoaderRegistryFactory
import com.rsba.usermicroservice.domain.model.Group
import com.rsba.usermicroservice.domain.model.User
import graphql.kickstart.tools.GraphQLResolver
import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.CompletableFuture

@Component
class UsersOfGroupResolver(
    private val logger: KLogger,
) : GraphQLResolver<Group> {

    fun users(group: Group, env: DataFetchingEnvironment): CompletableFuture<List<User>>? {
        logger.warn { "+---- UsersOfGroupResolver -> users" }
        val dataLoader = env.getDataLoader<UUID, List<User>>(DataLoaderRegistryFactory.USER_DATA_LOADER)
        return dataLoader?.load(group.id) ?: CompletableFuture.completedFuture(listOf())
    }

}