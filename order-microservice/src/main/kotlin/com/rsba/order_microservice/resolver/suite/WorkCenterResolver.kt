package com.rsba.order_microservice.resolver.suite

import com.rsba.order_microservice.data.context.dataloader.DataLoaderRegistryFactory
import com.rsba.order_microservice.domain.model.*
import graphql.kickstart.tools.GraphQLResolver
import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture

@Component
class WorkCenterResolver(private val logger: KLogger) : GraphQLResolver<WorkingCenter> {
    fun users(
        module: WorkingCenter,
        env: DataFetchingEnvironment
    ): CompletableFuture<List<User>>? {
        logger.warn { "+WorkCenterResolver -> users" }
        val dataLoader =
            env.getDataLoader<WorkingCenter, List<User>>(DataLoaderRegistryFactory.USER_IN_WORK_CENTER_WORKING_IN_TASK_DATALOADER)
        return dataLoader?.load(module) ?: CompletableFuture.completedFuture(emptyList())
    }
}