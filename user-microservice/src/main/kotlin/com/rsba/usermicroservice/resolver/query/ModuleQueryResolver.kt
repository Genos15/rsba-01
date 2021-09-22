package com.rsba.usermicroservice.resolver.query

import com.rsba.usermicroservice.configuration.request_helper.CursorUtil
import com.rsba.usermicroservice.domain.model.Module
import com.rsba.usermicroservice.interpector.aspect.AdminSecured
import com.rsba.usermicroservice.repository.ModuleRepository
import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.relay.*
import org.springframework.stereotype.Component

import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import java.util.*


@Component
class ModuleQueryResolver(
    private val logger: KLogger,
    val cursorUtil: CursorUtil,
    val service: ModuleRepository,
) : GraphQLQueryResolver {

    @AdminSecured
    suspend fun retrieveAllModules(
        first: Int,
        after: UUID? = null,
        env: DataFetchingEnvironment?
    ): Connection<Module>? {

        logger.warn { "+---- run = ModuleQueryResolver/retrieveAllModules" }

        val edges: List<Edge<Module>> = service.retrieveAllModule(first = first, after = after).map {
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