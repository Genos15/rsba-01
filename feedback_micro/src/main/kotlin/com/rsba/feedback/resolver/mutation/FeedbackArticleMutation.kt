package com.rsba.feedback.resolver.mutation

import  com.rsba.feedback.domain.input.*
import  com.rsba.feedback.aspect.AdminSecured
import com.rsba.feedback.context.token.TokenImpl
import com.rsba.feedback.domain.model.FeedbackArticle
import com.rsba.feedback.repository.FeedbackArticleRepository
import graphql.kickstart.tools.GraphQLMutationResolver
import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import org.springframework.stereotype.Component
import java.util.*

@Component
class FeedbackArticleMutation(
    private val service: FeedbackArticleRepository,
    private val logger: KLogger,
) : GraphQLMutationResolver, TokenImpl {

    @AdminSecured
    suspend fun createOrEditFeedbackArticle(
        input: FeedbackArticleInput,
        environment: DataFetchingEnvironment
    ): Optional<FeedbackArticle> {
        logger.warn { "+FeedbackArticleMutation->createOrEdit" }
        return service.createOrEdit(input = input, token = readToken(environment = environment))
    }

    @AdminSecured
    suspend fun deleteFeedbackArticle(input: UUID, environment: DataFetchingEnvironment): Boolean {
        logger.warn { "+FeedbackArticleMutation->delete" }
        return service.delete(input = input, token = readToken(environment = environment))
    }
}