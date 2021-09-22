package com.rsba.component_microservice.resolver.query

import com.rsba.component_microservice.configuration.request_helper.CursorUtil
import com.rsba.component_microservice.aspect.AdminSecured
import com.rsba.component_microservice.context.token.TokenImpl
import com.rsba.component_microservice.domain.model.Technology
import com.rsba.component_microservice.repository.TechnologyRepository
import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.relay.*
import org.springframework.stereotype.Component
import java.util.*

import graphql.schema.DataFetchingEnvironment
import mu.KLogger


@Component
class TechnologyQueryResolver(
    val cursorUtil: CursorUtil,
    val service: TechnologyRepository,
    val logger: KLogger,
    private val tokenImpl: TokenImpl
) : GraphQLQueryResolver {

    @AdminSecured
    suspend fun retrieveTechnologies(
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<Technology>? {

        logger.warn { "+TechnologyQueryResolver->retrieveTechnologies" }

        val edges: List<Edge<Technology>> =
            service.retrieve(
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

    @AdminSecured
    suspend fun searchTechnologies(content: String, environment: DataFetchingEnvironment): List<Technology>? {
        logger.warn { "+TechnologyQueryResolver->searchTechnologies" }
        return service.search(content = content, token = tokenImpl.read(environment = environment))
    }

    @AdminSecured
    suspend fun retrieveTechnologyById(id: UUID, environment: DataFetchingEnvironment): Optional<Technology> {
        logger.warn { "+TechnologyQueryResolver->retrieveTechnologyById" }
        return service.retrieveById(id = id, token = tokenImpl.read(environment = environment))
    }
}