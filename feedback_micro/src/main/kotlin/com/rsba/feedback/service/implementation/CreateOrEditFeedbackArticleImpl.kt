package com.rsba.feedback.service.implementation

import com.rsba.feedback.context.token.TokenImpl
import com.rsba.feedback.database.FeedbackArticleDBHandler
import com.rsba.feedback.database.FeedbackArticleQueries
import com.rsba.feedback.domain.input.FeedbackArticleInput
import com.rsba.feedback.domain.model.FeedbackArticle
import kotlinx.coroutines.reactive.awaitFirstOrElse
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface CreateOrEditFeedbackArticleImpl : TokenImpl {

    suspend fun createOrEditImpl(
        database: DatabaseClient,
        input: FeedbackArticleInput,
        token: UUID
    ): Optional<FeedbackArticle> = database.sql(FeedbackArticleQueries.createOrEdit(input = input, token = token))
        .map { row -> FeedbackArticleDBHandler.one(row = row) }.first()
        .onErrorResume {
            println("CreateOrEditFeedbackArticleImpl->createOrEdit->error=${it.message}")
            throw it
        }
        .awaitFirstOrElse { Optional.empty() }

}