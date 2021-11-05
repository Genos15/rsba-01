package com.rsba.component_microservice.resolver.query

import com.rsba.component_microservice.configuration.request_helper.CursorUtil
import com.rsba.component_microservice.domain.model.Operation
import com.rsba.component_microservice.aspect.AdminSecured
import com.rsba.component_microservice.data.context.token.TokenImpl
import com.rsba.component_microservice.domain.repository.OperationRepository
import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.relay.*
import org.springframework.stereotype.Component
import java.util.*

import graphql.schema.DataFetchingEnvironment
import mu.KLogger


@Component
class OperationQueryResolver(
    val cursorUtil: CursorUtil,
    val service: OperationRepository,
    val logger: KLogger,
    private val tokenImpl: TokenImpl
) : GraphQLQueryResolver {

    @AdminSecured
    suspend fun retrieveAllOperation(
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<Operation>? {

        logger.warn { "+OperationQueryResolver -> retrieveAllOperation" }

        val edges: List<Edge<Operation>> =
            service.retrieveAllOperation(
                first = first,
                after = after,
                token = tokenImpl.read(environment = environment)
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
}