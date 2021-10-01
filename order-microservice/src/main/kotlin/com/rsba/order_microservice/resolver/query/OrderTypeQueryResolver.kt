package com.rsba.order_microservice.resolver.query


import com.rsba.order_microservice.aspect.AdminSecured
import com.rsba.order_microservice.configuration.request_helper.CursorUtil
import com.rsba.order_microservice.context.token.ITokenImpl
import com.rsba.order_microservice.domain.model.OrderType
import com.rsba.order_microservice.repository.OrderTypeRepository
import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.relay.*
import org.springframework.stereotype.Component
import java.util.*

import graphql.schema.DataFetchingEnvironment
import mu.KLogger


@Component
class OrderTypeQueryResolver(
    val cursorUtil: CursorUtil,
    val service: OrderTypeRepository,
    val logger: KLogger
) : GraphQLQueryResolver, ITokenImpl {

    @AdminSecured
    suspend fun retrieveAllOrderType(
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<OrderType>? {

        logger.warn { "+OrderTypeQueryResolver->retrieve" }

        val edges: List<Edge<OrderType>> =
            service.retrieve(
                first = first,
                after = after,
                token = readToken(environment = environment)
            ).map {
                return@map DefaultEdge(it, cursorUtil.createCursorWith(it.id))
            }.take(first)

        val pageInfo = DefaultPageInfo(
            cursorUtil.firstCursorFrom(edges),
            cursorUtil.lastCursorFrom(edges),
            after != null,
            edges.size >= first
        )

        return DefaultConnection(edges, pageInfo)
    }

    @AdminSecured
    suspend fun searchOrderType(
        input: String,
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<OrderType>? {

        logger.warn { "+OrderTypeQueryResolver->search" }

        val edges: List<Edge<OrderType>> =
            service.search(
                input = input,
                first = first,
                after = after,
                token = readToken(environment = environment)
            ).map {
                return@map DefaultEdge(it, cursorUtil.createCursorWith(it.id))
            }.take(first)

        val pageInfo = DefaultPageInfo(
            cursorUtil.firstCursorFrom(edges),
            cursorUtil.lastCursorFrom(edges),
            after != null,
            edges.size >= first
        )

        return DefaultConnection(edges, pageInfo)
    }

    @AdminSecured
    suspend fun retrieveOrderTypeById(id: UUID, environment: DataFetchingEnvironment): Optional<OrderType> {
        logger.warn { "+OrderTypeQueryResolver->retrieveFeedbackArticleById" }
        return service.retrieveById(id = id, token = readToken(environment = environment))
    }
}