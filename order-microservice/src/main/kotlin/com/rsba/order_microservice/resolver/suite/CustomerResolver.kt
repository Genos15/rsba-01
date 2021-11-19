package com.rsba.order_microservice.resolver.suite

import com.rsba.order_microservice.data.context.dataloader.DataLoaderRegistryFactory
import com.rsba.order_microservice.domain.model.Customer
import graphql.kickstart.tools.GraphQLResolver
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.CompletableFuture

@Component
class CustomerResolver : GraphQLResolver<Customer> {

    fun entities(customer: Customer, env: DataFetchingEnvironment): CompletableFuture<List<Customer>> {
        val dataLoader =
            env.getDataLoader<UUID, List<Customer>>(DataLoaderRegistryFactory.LOADER_FACTORY_ENTITIES_OF_CUSTOMER)
        return dataLoader?.load(customer.id) ?: CompletableFuture.completedFuture(emptyList())
    }

}