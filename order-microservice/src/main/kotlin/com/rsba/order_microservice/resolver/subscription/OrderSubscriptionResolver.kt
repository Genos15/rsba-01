package com.rsba.order_microservice.resolver.subscription

//import com.rsba.order_microservice.domain.model.OrderForSub
import com.rsba.order_microservice.data.publisher.OrderPublisher
import graphql.kickstart.tools.GraphQLSubscriptionResolver
//import graphql.schema.DataFetchingEnvironment
//import org.reactivestreams.Publisher
import org.springframework.stereotype.Component

@Component
class OrderSubscriptionResolver(private val publisher: OrderPublisher) : GraphQLSubscriptionResolver {
//    fun all(environment: DataFetchingEnvironment?): Publisher<OrderForSub> = publisher.get(environment = environment)
}