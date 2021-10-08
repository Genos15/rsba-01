package com.rsba.order_microservice.resolver.query


import com.rsba.order_microservice.aspect.AdminSecured
import com.rsba.order_microservice.configuration.request_helper.CursorUtil
import com.rsba.order_microservice.context.token.ITokenImpl
import com.rsba.order_microservice.domain.model.Parameter
import com.rsba.order_microservice.repository.ParameterRepository
import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.relay.*
import org.springframework.stereotype.Component
import java.util.*

import graphql.schema.DataFetchingEnvironment
import mu.KLogger


@Component
class ParameterQueryResolver(
    val cursorUtil: CursorUtil,
    val service: ParameterRepository,
    val logger: KLogger
) : GraphQLQueryResolver, ITokenImpl {

    @AdminSecured
    suspend fun retrieveAllParameters(
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<Parameter>? {
        logger.warn { "retrieveAllParameters" }
        val edges: List<Edge<Parameter>> =
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
    suspend fun searchParameters(
        input: String,
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<Parameter>? {
        val edges: List<Edge<Parameter>> =
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
    suspend fun retrieveParametersByTaskId(taskId: UUID, environment: DataFetchingEnvironment): List<Parameter> =
        service.retrieveByTaskId(taskId = taskId, token = readToken(environment = environment))

    @AdminSecured
    suspend fun retrieveParametersByItemId(itemId: UUID, environment: DataFetchingEnvironment): List<Parameter> =
        service.retrieveByItemId(itemId = itemId, token = readToken(environment = environment))
}