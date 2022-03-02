package com.rsba.tasks_microservice.resolver.suite

import com.rsba.tasks_microservice.data.context.dataloader.DataLoaderRegistryFactory
import com.rsba.tasks_microservice.domain.model.*
import graphql.kickstart.tools.GraphQLResolver
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import java.util.*

import java.util.concurrent.CompletableFuture

@Component
class GanttResolver : GraphQLResolver<GanttData> {

    fun item(input: GanttData, env: DataFetchingEnvironment): CompletableFuture<Optional<Item>> {
        val dataLoader =
            env.getDataLoader<UUID, Optional<Item>>(DataLoaderRegistryFactory.LOADER_FACTORY_ITEM_OF_TASK)
        return dataLoader?.load(input.TaskID) ?: CompletableFuture.completedFuture(Optional.empty())
    }

    fun operation(input: GanttData, env: DataFetchingEnvironment): CompletableFuture<Optional<Operation>> {
        val dataLoader =
            env.getDataLoader<UUID, Optional<Operation>>(DataLoaderRegistryFactory.LOADER_FACTORY_OPERATION_OF_TASK)
        return dataLoader?.load(input.TaskID) ?: CompletableFuture.completedFuture(Optional.empty())
    }
}