package com.rsba.feedback.service.implementation

import com.rsba.feedback.context.token.TokenImpl
import com.rsba.feedback.database.FeedbackArticleDBHandler
import com.rsba.feedback.database.FeedbackArticleQueries
import com.rsba.feedback.domain.model.FeedbackArticle
import kotlinx.coroutines.reactive.awaitFirstOrElse
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface RetrieveFeedbackArticleImpl : TokenImpl {

    suspend fun retrieveImpl(
        database: DatabaseClient,
        input: String? = null,
        first: Int,
        after: UUID? = null,
        token: UUID
    ): List<FeedbackArticle> = database.sql(
        input?.let {
            FeedbackArticleQueries.search(input = input, token = token, first = first, after = after)
        } ?: FeedbackArticleQueries.retrieve(token = token, first = first, after = after)
    ).map { row -> FeedbackArticleDBHandler.all(row = row) }.first()
        .onErrorResume {
            println("RetrieveFeedbackArticleImpl->retrieve->error=${it.message}")
            throw it
        }
        .awaitFirstOrElse { emptyList() }

    suspend fun retrieveByIdImpl(
        database: DatabaseClient,
        id: UUID,
        token: UUID
    ): Optional<FeedbackArticle> = database.sql(FeedbackArticleQueries.retrieveById(id = id, token = token))
        .map { row -> FeedbackArticleDBHandler.one(row = row) }.first()
        .onErrorResume {
            println("RetrieveFeedbackArticleImpl->retrieveById->error=${it.message}")
            throw it
        }
        .awaitFirstOrElse { Optional.empty() }
}