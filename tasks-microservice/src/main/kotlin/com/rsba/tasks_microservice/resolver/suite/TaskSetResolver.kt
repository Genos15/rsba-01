package com.rsba.tasks_microservice.resolver.suite

import com.rsba.tasks_microservice.data.context.dataloader.DataLoaderRegistryFactory
import com.rsba.tasks_microservice.domain.model.*
import graphql.kickstart.tools.GraphQLResolver
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import java.util.*

import java.util.concurrent.CompletableFuture

@Component
class TaskSetResolver : GraphQLResolver<TaskSet> {

    fun users(input: TaskSet, env: DataFetchingEnvironment): CompletableFuture<List<User>> {
        val dataLoader =
            env.getDataLoader<UUID, List<User>>(DataLoaderRegistryFactory.LOADER_FACTORY_USERS_OF_TASKSET)
        return dataLoader?.load(input.id) ?: CompletableFuture.completedFuture(emptyList())
    }

    fun tasks(input: TaskSet, env: DataFetchingEnvironment): CompletableFuture<List<Task>> {
        val dataLoader =
            env.getDataLoader<UUID, List<Task>>(DataLoaderRegistryFactory.LOADER_FACTORY_TASKS_OF_TASKSET)
        return dataLoader?.load(input.id) ?: CompletableFuture.completedFuture(emptyList())
    }

    fun comments(input: TaskSet, env: DataFetchingEnvironment): CompletableFuture<List<Comment>> {
        val dataLoader =
            env.getDataLoader<UUID, List<Comment>>(DataLoaderRegistryFactory.LOADER_FACTORY_COMMENTS_OF_TASKSET)
        return dataLoader?.load(input.id) ?: CompletableFuture.completedFuture(emptyList())
    }

}