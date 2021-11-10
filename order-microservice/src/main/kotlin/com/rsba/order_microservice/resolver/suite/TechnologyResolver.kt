package com.rsba.order_microservice.resolver.suite

import com.rsba.order_microservice.data.context.dataloader.DataLoaderRegistryFactory
import com.rsba.order_microservice.domain.model.Operation
import com.rsba.order_microservice.domain.model.Technology
import graphql.kickstart.tools.GraphQLResolver
import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.CompletableFuture

@Component
class TechnologyResolver(private val logger: KLogger) : GraphQLResolver<Technology> {

    fun operations(technology: Technology, env: DataFetchingEnvironment): CompletableFuture<List<Operation>>? {
        logger.warn { "+TechnologyResolver->operations" }
        val dataLoader =
            env.getDataLoader<UUID, List<Operation>>(DataLoaderRegistryFactory.OPERATION_IN_TECHNOLOGY_DATALOADER)
        return dataLoader?.load(technology.id) ?: CompletableFuture.completedFuture(null)
    }

}