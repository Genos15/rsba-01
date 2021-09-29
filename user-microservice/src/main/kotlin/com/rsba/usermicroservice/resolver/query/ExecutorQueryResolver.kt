package com.rsba.usermicroservice.resolver.query


import com.rsba.usermicroservice.configuration.request_helper.CursorUtil
import com.rsba.usermicroservice.context.token.ITokenImpl
import com.rsba.usermicroservice.domain.model.Executor
import com.rsba.usermicroservice.interpector.aspect.AdminSecured
import com.rsba.usermicroservice.repository.ExecutorRepository
import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.relay.*
import org.springframework.stereotype.Component
import java.util.*

import graphql.schema.DataFetchingEnvironment
import mu.KLogger


@Component
class ExecutorQueryResolver(
    val cursorUtil: CursorUtil,
    val service: ExecutorRepository,
    val logger: KLogger
) : GraphQLQueryResolver, ITokenImpl {

    @AdminSecured
    suspend fun retrieveAllExecutors(
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<Executor>? {
        logger.warn { "+ExecutorQueryResolver->retrieveAllExecutors" }
        val edges: List<Edge<Executor>> =
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
    suspend fun searchExecutors(
        input: String,
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<Executor>? {
        logger.warn { "+ExecutorQueryResolver->searchExecutors" }
        val edges: List<Edge<Executor>> =
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
    suspend fun retrieveExecutorById(id: UUID, environment: DataFetchingEnvironment): Optional<Executor> {
        logger.warn { "+ExecutorQueryResolver->retrieveExecutorById" }
        return service.retrieveById(id = id, token = readToken(environment = environment))
    }
}