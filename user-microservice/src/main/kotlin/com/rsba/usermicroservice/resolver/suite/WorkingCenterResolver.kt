package com.rsba.usermicroservice.resolver.suite

import com.rsba.usermicroservice.context.dataloader.DataLoaderRegistryFactory
import com.rsba.usermicroservice.domain.model.User
import com.rsba.usermicroservice.domain.model.WorkingCenter
import graphql.kickstart.tools.GraphQLResolver
import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.CompletableFuture

@Component
class WorkingCenterResolver(
    private val logger: KLogger,
) : GraphQLResolver<WorkingCenter> {

    fun users(center: WorkingCenter, env: DataFetchingEnvironment): CompletableFuture<List<User>?>? {
        logger.warn { "+WorkingCenterResolver -> users" }
        val dataLoader =
            env.getDataLoader<UUID, List<User>?>(DataLoaderRegistryFactory.USER_IN_WORKING_CENTER_DATA_LOADER)
        return dataLoader?.load(center.id) ?: CompletableFuture.completedFuture(null)
    }

    fun managers(center: WorkingCenter, env: DataFetchingEnvironment): CompletableFuture<List<User>?>? {
        logger.warn { "+WorkingCenterResolver -> managers" }
        val dataLoader =
            env.getDataLoader<UUID, List<User>?>(DataLoaderRegistryFactory.MANAGERS_IN_WORKING_CENTER_DATA_LOADER)
        return dataLoader?.load(center.id) ?: CompletableFuture.completedFuture(null)
    }

}