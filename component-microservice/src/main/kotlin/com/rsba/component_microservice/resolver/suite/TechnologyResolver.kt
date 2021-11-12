package com.rsba.component_microservice.resolver.suite

import com.rsba.component_microservice.data.context.dataloader.DataLoaderRegistryFactory
import com.rsba.component_microservice.domain.model.Operation
import com.rsba.component_microservice.domain.model.Technology
import graphql.kickstart.tools.GraphQLResolver
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.CompletableFuture

@Component
class TechnologyResolver : GraphQLResolver<Technology> {
    fun operations(technology: Technology, env: DataFetchingEnvironment): CompletableFuture<List<Operation>> {
        val dataLoader =
            env.getDataLoader<UUID, List<Operation>>(DataLoaderRegistryFactory.OPERATION_IN_TECHNOLOGY_DATALOADER)
        return dataLoader?.load(technology.id) ?: CompletableFuture.completedFuture(emptyList())
    }
}