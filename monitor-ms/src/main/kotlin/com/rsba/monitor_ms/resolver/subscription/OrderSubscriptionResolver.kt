package com.rsba.monitor_ms.resolver.subscription

import com.rsba.monitor_ms.domain.model.Order
import com.rsba.monitor_ms.publisher.OrderPublisher
import com.rsba.monitor_ms.repository.OrderRepository
import graphql.kickstart.tools.GraphQLSubscriptionResolver
import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import org.reactivestreams.Publisher
import org.springframework.stereotype.Component

@Component
class OrderSubscriptionResolver(
    private val logger: KLogger,
    private val service: OrderRepository,
    private val publisher: OrderPublisher
) : GraphQLSubscriptionResolver {

    fun all(environment: DataFetchingEnvironment): Publisher<List<Order>> = publisher.get()
}