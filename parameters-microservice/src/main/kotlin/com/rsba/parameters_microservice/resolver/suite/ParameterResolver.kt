package com.rsba.parameters_microservice.resolver.suite

//import com.rsba.parameters_microservice.data.context.dataloader.DataLoaderRegistryFactory
import com.rsba.parameters_microservice.domain.model.Parameter
import graphql.kickstart.tools.GraphQLResolver
//import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component

//import java.util.*
//import java.util.concurrent.CompletableFuture

@Component
class ParameterResolver : GraphQLResolver<Parameter> {

//    fun values(input: Parameter, env: DataFetchingEnvironment): CompletableFuture<List<String>> {
//        val dataLoader =
//            env.getDataLoader<UUID, List<String>>(DataLoaderRegistryFactory.LOADER_FACTORY_VALUES_OF_PARAMETER)
//        return dataLoader?.load(input.id) ?: CompletableFuture.completedFuture(emptyList())
//    }

}