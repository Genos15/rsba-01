package com.rsba.monitor_ms.resolver.suite

import com.rsba.monitor_ms.context.dataloader.DataLoaderRegistryFactory
import com.rsba.monitor_ms.domain.model.Customer
import graphql.kickstart.tools.GraphQLResolver
import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.CompletableFuture

@Component
class EntitiesOfCustomerResolver(
    private val logger: KLogger,
) : GraphQLResolver<Customer> {

    fun entities(customer: Customer, env: DataFetchingEnvironment): CompletableFuture<MutableList<Customer>>? {
        logger.warn { "+---- EntitiesOfCustomerResolver -> entities" }
        val dataLoader = env.getDataLoader<UUID, MutableList<Customer>>(DataLoaderRegistryFactory.CUSTOMERS_IN_CUSTOMER)
        return dataLoader?.load(customer.id) ?: CompletableFuture.completedFuture(mutableListOf())
    }

}