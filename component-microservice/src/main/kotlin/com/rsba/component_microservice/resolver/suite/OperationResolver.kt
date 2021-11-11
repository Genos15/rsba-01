package com.rsba.component_microservice.resolver.suite

import com.rsba.component_microservice.data.context.dataloader.DataLoaderRegistryFactory
import com.rsba.component_microservice.domain.model.Group
import com.rsba.component_microservice.domain.model.Operation
import graphql.kickstart.tools.GraphQLResolver
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.CompletableFuture

@Component
class OperationResolver : GraphQLResolver<Operation> {

    fun departments(operation: Operation, env: DataFetchingEnvironment): CompletableFuture<List<Group>>? {
        val dataLoader = env.getDataLoader<UUID, List<Group>>(DataLoaderRegistryFactory.GROUP_IN_OPERATION_DATALOADER)
        return dataLoader?.load(operation.id) ?: CompletableFuture.completedFuture(emptyList())
    }

}