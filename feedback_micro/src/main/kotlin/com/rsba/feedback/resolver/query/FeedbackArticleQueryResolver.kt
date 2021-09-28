package com.rsba.feedback.resolver.query

import com.rsba.feedback.configuration.request_helper.CursorUtil
import com.rsba.feedback.aspect.AdminSecured
import com.rsba.feedback.context.token.TokenImpl
import com.rsba.feedback.domain.model.FeedbackArticle
import com.rsba.feedback.repository.FeedbackArticleRepository
import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.relay.*
import org.springframework.stereotype.Component
import java.util.*

import graphql.schema.DataFetchingEnvironment
import mu.KLogger


@Component
class FeedbackArticleQueryResolver(
    val cursorUtil: CursorUtil,
    val service: FeedbackArticleRepository,
    val logger: KLogger
) : GraphQLQueryResolver, TokenImpl {

    @AdminSecured
    suspend fun retrieveAllFeedbackArticle(
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<FeedbackArticle>? {

        logger.warn { "+FeedbackArticleQueryResolver->retrieve" }

        val edges: List<Edge<FeedbackArticle>> =
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
    suspend fun searchFeedbackArticle(
        input: String,
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<FeedbackArticle>? {

        logger.warn { "+FeedbackArticleQueryResolver->search" }

        val edges: List<Edge<FeedbackArticle>> =
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
    suspend fun retrieveFeedbackArticleById(id: UUID, environment: DataFetchingEnvironment): Optional<FeedbackArticle> {
        logger.warn { "+FeedbackArticleQueryResolver->retrieveFeedbackArticleById" }
        return service.retrieveById(id = id, token = readToken(environment = environment))
    }
}