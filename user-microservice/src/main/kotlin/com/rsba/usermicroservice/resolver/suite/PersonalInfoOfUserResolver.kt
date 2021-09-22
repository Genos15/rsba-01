package com.rsba.usermicroservice.resolver.suite

import com.rsba.usermicroservice.context.dataloader.DataLoaderRegistryFactory
import com.rsba.usermicroservice.domain.model.PersonalInfo
import com.rsba.usermicroservice.domain.model.User
import graphql.kickstart.tools.GraphQLResolver
import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.CompletableFuture

@Component
class PersonalInfoOfUserResolver(
    private val logger: KLogger,
) : GraphQLResolver<User> {

    fun personalInfo(user: User, env: DataFetchingEnvironment): CompletableFuture<PersonalInfo?>? {
        logger.warn { "+PersonalInfoOfUserResolver -> personalInfo" }
        val dataLoader = env.getDataLoader<UUID, PersonalInfo?>(DataLoaderRegistryFactory.PERSONAL_INFO_DATA_LOADER)
        return dataLoader?.load(user.id) ?: CompletableFuture.completedFuture(null)
    }

}