package com.rsba.usermicroservice.resolver.query

import com.rsba.usermicroservice.configuration.request_helper.CursorUtil
import com.rsba.usermicroservice.context.token.TokenImpl
import com.rsba.usermicroservice.domain.model.AnalysisTemplate
import com.rsba.usermicroservice.interpector.aspect.AdminSecured
import com.rsba.usermicroservice.repository.AnalysisTemplateRepository
import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.relay.*
import org.springframework.stereotype.Component

import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import java.util.*


@Component
class AnalysisTemplateQueryResolver(
    private val logger: KLogger,
    val cursorUtil: CursorUtil,
    val service: AnalysisTemplateRepository,
    private val tokenImpl: TokenImpl
) : GraphQLQueryResolver {

    @AdminSecured
    suspend fun retrieveAllAnalysisTemplates(
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<AnalysisTemplate>? {
        logger.warn { "+AnalysisTemplateQueryResolver -> retrieveAllAnalysisTemplates" }
        val edges: List<Edge<AnalysisTemplate>> =
            service.retrieveAllAnalysisTemplates(
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