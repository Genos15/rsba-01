package com.rsba.usermicroservice.resolver.suite

import com.rsba.usermicroservice.context.dataloader.DataLoaderRegistryFactory
import com.rsba.usermicroservice.domain.model.Role
import com.rsba.usermicroservice.domain.model.User
import graphql.kickstart.tools.GraphQLResolver
import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.CompletableFuture

@Component
class RoleOfUserResolver(
    private val logger: KLogger,
) : GraphQLResolver<User> {

    fun role(user: User, env: DataFetchingEnvironment): CompletableFuture<Role?>? {
        logger.warn { "+---- RoleOfUserResolver -> role" }
        val dataLoader = env.getDataLoader<UUID, Role?>(DataLoaderRegistryFactory.ROLE_DATA_LOADER_GENERIC)
        return dataLoader?.load(user.id) ?: CompletableFuture.completedFuture(null)
    }

}