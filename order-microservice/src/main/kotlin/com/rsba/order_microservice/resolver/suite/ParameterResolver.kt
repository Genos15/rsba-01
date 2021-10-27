package com.rsba.order_microservice.resolver.suite

import com.rsba.order_microservice.context.dataloader.DataLoaderRegistryFactory
import com.rsba.order_microservice.domain.model.Parameter
import graphql.kickstart.tools.GraphQLResolver
import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.CompletableFuture

@Component
class ParameterResolver(private val logger: KLogger) : GraphQLResolver<Parameter> {

    fun potentialValues(parameter: Parameter, env: DataFetchingEnvironment): CompletableFuture<List<String>> {
        logger.warn { "+ParameterResolver->potentialValues" }
        val dataLoader =
            env.getDataLoader<UUID, List<String>>(DataLoaderRegistryFactory.POTENTIAL_VALUES_PARAMETER_DATALOADER)
        return dataLoader?.load(parameter.id) ?: CompletableFuture.completedFuture(emptyList())
    }

}