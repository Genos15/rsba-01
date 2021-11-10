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
class UserResolver(private val logger: KLogger) : GraphQLResolver<User> {

    fun personalInfo(instance: User, env: DataFetchingEnvironment): CompletableFuture<PersonalInfo>? {
        logger.warn { "+UserResolver -> personalInfo" }
        val dataLoader =
            env.getDataLoader<UUID, PersonalInfo>(DataLoaderRegistryFactory.PERSONAL_INFO_OF_USER_DATALOADER)
        return dataLoader?.load(instance.id) ?: CompletableFuture.completedFuture(null)
    }

    fun contactInfo(instance: User, env: DataFetchingEnvironment): CompletableFuture<List<ContactInfo>>? {
        logger.warn { "+UserResolver -> contactInfo" }
        val dataLoader =
            env.getDataLoader<UUID, List<ContactInfo>>(DataLoaderRegistryFactory.CONTACT_INFO_OF_USER_DATALOADER)
        return dataLoader?.load(instance.id) ?: CompletableFuture.completedFuture(emptyList())
    }
}