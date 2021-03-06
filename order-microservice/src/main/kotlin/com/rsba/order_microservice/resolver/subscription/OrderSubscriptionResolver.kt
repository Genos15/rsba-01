package com.rsba.order_microservice.resolver.subscription

import com.rsba.order_microservice.domain.model.OrderForSub
import com.rsba.order_microservice.publisher.OrderPublisher
import graphql.kickstart.tools.GraphQLSubscriptionResolver
import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import org.reactivestreams.Publisher
import org.springframework.stereotype.Component

@Component
class OrderSubscriptionResolver(private val publisher: OrderPublisher, private val logger: KLogger) :
    GraphQLSubscriptionResolver {

    fun all(environment: DataFetchingEnvironment?): Publisher<OrderForSub> = publisher.get(environment = environment)
}