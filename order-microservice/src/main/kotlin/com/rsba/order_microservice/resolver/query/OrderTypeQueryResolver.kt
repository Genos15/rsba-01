package com.rsba.order_microservice.resolver.query


import com.rsba.order_microservice.aspect.AdminSecured
import com.rsba.order_microservice.configuration.request_helper.CursorUtil
import com.rsba.order_microservice.data.context.token.ITokenImpl
import com.rsba.order_microservice.domain.model.Customer
import com.rsba.order_microservice.domain.model.OrderType
import com.rsba.order_microservice.domain.repository.CustomerRepository
import com.rsba.order_microservice.domain.repository.OrderTypeRepository
import com.rsba.order_microservice.domain.security.TokenAnalyzer
import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.relay.*
import org.springframework.stereotype.Component
import java.util.*

import graphql.schema.DataFetchingEnvironment
import mu.KLogger


@Component
class OrderTypeQueryResolver(private val service: OrderTypeRepository, private val deduct: TokenAnalyzer) :
    GraphQLQueryResolver, GenericRetrieveConnection {

    @AdminSecured
    suspend fun retrieveOrderTypes(
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<OrderType> = perform(
        entries = service.retrieve(
            first = first,
            after = after,
            token = deduct(environment = environment)
        ),
        first = first,
        after = after
    )

    @AdminSecured
    suspend fun searchOrderTypes(
        input: String,
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<OrderType> = perform(
        entries = service.search(
            input = input,
            first = first,
            after = after,
            token = deduct(environment = environment)
        ),
        first = first,
        after = after
    )

    suspend fun findOrderType(id: UUID, environment: DataFetchingEnvironment): Optional<OrderType> =
        service.find(id = id, token = deduct(environment = environment))

    suspend fun countOrderTypes(environment: DataFetchingEnvironment): Int =
        service.count(token = deduct(environment = environment))
}