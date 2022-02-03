package com.rsba.tasks_microservice.resolver.suite

import com.rsba.tasks_microservice.data.context.dataloader.DataLoaderRegistryFactory
import com.rsba.tasks_microservice.domain.model.*
import graphql.kickstart.tools.GraphQLResolver
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import java.util.*

import java.util.concurrent.CompletableFuture

@Component
class TaskResolver : GraphQLResolver<Task> {

    fun order(input: Task, env: DataFetchingEnvironment): CompletableFuture<Optional<Order>> {
        val dataLoader =
            env.getDataLoader<UUID, Optional<Order>>(DataLoaderRegistryFactory.LOADER_FACTORY_ORDER_OF_TASK)
        return dataLoader?.load(input.id) ?: CompletableFuture.completedFuture(Optional.empty())
    }

    fun item(input: Task, env: DataFetchingEnvironment): CompletableFuture<Optional<Item>> {
        val dataLoader =
            env.getDataLoader<UUID, Optional<Item>>(DataLoaderRegistryFactory.LOADER_FACTORY_ITEM_OF_TASK)
        return dataLoader?.load(input.id) ?: CompletableFuture.completedFuture(Optional.empty())
    }

    fun operation(input: Task, env: DataFetchingEnvironment): CompletableFuture<Optional<Operation>> {
        val dataLoader =
            env.getDataLoader<UUID, Optional<Operation>>(DataLoaderRegistryFactory.LOADER_FACTORY_OPERATION_OF_TASK)
        return dataLoader?.load(input.id) ?: CompletableFuture.completedFuture(Optional.empty())
    }

    fun workcenter(input: Task, env: DataFetchingEnvironment): CompletableFuture<Optional<Workcenter>> {
        val dataLoader =
            env.getDataLoader<UUID, Optional<Workcenter>>(DataLoaderRegistryFactory.LOADER_FACTORY_WORKCENTER_OF_TASK)
        return dataLoader?.load(input.id) ?: CompletableFuture.completedFuture(Optional.empty())
    }

    fun users(input: Task, env: DataFetchingEnvironment): CompletableFuture<List<User>> {
        val dataLoader =
            env.getDataLoader<UUID, List<User>>(DataLoaderRegistryFactory.LOADER_FACTORY_USERS_OF_TASK)
        return dataLoader?.load(input.id) ?: CompletableFuture.completedFuture(emptyList())
    }

    fun comments(input: Task, env: DataFetchingEnvironment): CompletableFuture<List<Comment>> {
        val dataLoader =
            env.getDataLoader<UUID, List<Comment>>(DataLoaderRegistryFactory.LOADER_FACTORY_COMMENTS_OF_TASK)
        return dataLoader?.load(input.id) ?: CompletableFuture.completedFuture(emptyList())
    }

    fun technologies(input: Task, env: DataFetchingEnvironment): CompletableFuture<List<Technology>> {
        val dataLoader =
            env.getDataLoader<UUID, List<Technology>>(DataLoaderRegistryFactory.LOADER_FACTORY_TECHNOLOGIES_OF_TASK)
        return dataLoader?.load(input.id) ?: CompletableFuture.completedFuture(emptyList())
    }

    fun worklogs(input: Task, env: DataFetchingEnvironment): CompletableFuture<List<Worklog>> {
        val dataLoader =
            env.getDataLoader<UUID, List<Worklog>>(DataLoaderRegistryFactory.LOADER_FACTORY_WORKLOGS_OF_TASK)
        return dataLoader?.load(input.id) ?: CompletableFuture.completedFuture(emptyList())
    }

}