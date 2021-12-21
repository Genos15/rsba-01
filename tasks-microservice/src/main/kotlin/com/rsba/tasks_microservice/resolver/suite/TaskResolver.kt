package com.rsba.tasks_microservice.resolver.suite

//import com.rsba.tasks_microservice.data.context.dataloader.DataLoaderRegistryFactory
import com.rsba.tasks_microservice.domain.model.Task
import graphql.kickstart.tools.GraphQLResolver
//import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component

//import java.util.*
//import java.util.concurrent.CompletableFuture

@Component
class TaskResolver : GraphQLResolver<Task> {

//    fun values(input: Parameter, env: DataFetchingEnvironment): CompletableFuture<List<String>> {
//        val dataLoader =
//            env.getDataLoader<UUID, List<String>>(DataLoaderRegistryFactory.LOADER_FACTORY_VALUES_OF_PARAMETER)
//        return dataLoader?.load(input.id) ?: CompletableFuture.completedFuture(emptyList())
//    }

}