package com.rsba.usermicroservice.resolver.suite

import com.rsba.usermicroservice.context.dataloader.DataLoaderRegistryFactory
import com.rsba.usermicroservice.domain.model.Group
import com.rsba.usermicroservice.domain.model.User
import com.rsba.usermicroservice.domain.model.WorkingCenter
import graphql.kickstart.tools.GraphQLResolver
import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.CompletableFuture

@Component
class GroupsResolver(private val logger: KLogger) : GraphQLResolver<Group> {

    fun workingCenters(entry: Group, env: DataFetchingEnvironment): CompletableFuture<List<WorkingCenter>?>? {
        logger.warn { "+GroupsResolver -> workingCenters" }
        val dataLoader =
            env.getDataLoader<UUID, List<WorkingCenter>?>(DataLoaderRegistryFactory.WORKING_CENTER_IN_GROUP_DATA_LOADER)
        return dataLoader?.load(entry.id) ?: CompletableFuture.completedFuture(null)
    }

    fun managers(entry: Group, env: DataFetchingEnvironment): CompletableFuture<List<User>?>? {
        logger.warn { "+GroupsResolver -> managers" }
        val dataLoader =
            env.getDataLoader<UUID, List<User>?>(DataLoaderRegistryFactory.MANAGERS_IN_GROUP_DATA_LOADER)
        return dataLoader?.load(entry.id) ?: CompletableFuture.completedFuture(null)
    }

}