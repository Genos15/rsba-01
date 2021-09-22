package com.rsba.usermicroservice.resolver.suite

import com.rsba.usermicroservice.context.dataloader.DataLoaderRegistryFactory
import com.rsba.usermicroservice.domain.model.ContactInfo
import com.rsba.usermicroservice.domain.model.Group
import com.rsba.usermicroservice.domain.model.User
import graphql.kickstart.tools.GraphQLResolver
import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.CompletableFuture

@Component
class UserResolver(private val logger: KLogger) : GraphQLResolver<User> {

    fun contactInfo(user: User, env: DataFetchingEnvironment): CompletableFuture<List<ContactInfo>>? {
        logger.warn { "+ContactsOfUserResolver -> contactInfo" }
        val dataLoader = env.getDataLoader<UUID, List<ContactInfo>>(DataLoaderRegistryFactory.CONTACT_INFO_DATA_LOADER)
        return dataLoader?.load(user.id) ?: CompletableFuture.completedFuture(emptyList())
    }

    fun departments(input: User, env: DataFetchingEnvironment): CompletableFuture<List<Group?>>? {
        logger.warn { "+UserResolver -> departments" }
        val dataLoader =
            env.getDataLoader<UUID, List<Group?>>(DataLoaderRegistryFactory.GROUPS_OF_USERS_DATA_LOADER)
        return dataLoader?.load(input.id) ?: CompletableFuture.completedFuture(emptyList())
    }

}