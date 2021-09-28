package com.rsba.feedback.service.implementation

import com.rsba.feedback.context.token.TokenImpl
import com.rsba.feedback.database.FeedbackArticleDBHandler
import com.rsba.feedback.database.FeedbackArticleQueries
import com.rsba.feedback.domain.input.FeedbackArticleInput
import com.rsba.feedback.domain.model.FeedbackArticle
import kotlinx.coroutines.reactive.awaitFirstOrElse
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface DeleteFeedbackArticleImpl : TokenImpl {

    suspend fun deleteImpl(
        database: DatabaseClient,
        input: UUID,
        token: UUID
    ): Boolean = database.sql(FeedbackArticleQueries.delete(input = input, token = token))
        .map { row -> FeedbackArticleDBHandler.count(row = row) }.first()
        .map { it != null && it > 0 }
        .onErrorResume {
            println("DeleteFeedbackArticleImpl->deleteImpl->error=${it.message}")
            throw it
        }
        .awaitFirstOrElse { false }

}