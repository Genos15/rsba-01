package com.rsba.feedback.service

import com.rsba.feedback.domain.input.FeedbackArticleInput
import com.rsba.feedback.domain.model.FeedbackArticle
import com.rsba.feedback.repository.FeedbackArticleRepository
import com.rsba.feedback.service.implementation.CreateOrEditFeedbackArticleImpl
import com.rsba.feedback.service.implementation.DeleteFeedbackArticleImpl
import com.rsba.feedback.service.implementation.RetrieveFeedbackArticleImpl
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import java.util.*

@Service
class FeedbackArticleService(private val database: DatabaseClient) : FeedbackArticleRepository,
    CreateOrEditFeedbackArticleImpl, DeleteFeedbackArticleImpl, RetrieveFeedbackArticleImpl {

    /**
     * @param token the key allowing the request to proceed.
     * @param input main Args, feedback object to save.
     * @return {@link List<FeedbackArticle>}
     */
    override suspend fun createOrEdit(input: FeedbackArticleInput, token: UUID): Optional<FeedbackArticle> =
        createOrEditImpl(input = input, token = token, database = database)

    /**
     * @param token the key allowing the request to proceed.
     * @param input, unique reference of the feedback to delete.
     * @return {@link <Boolean> depending on the result of the request}
     */
    override suspend fun delete(input: UUID, token: UUID): Boolean =
        deleteImpl(database = database, input = input, token = token)

    /**
     * @param token the key allowing the request to proceed.
     * @param first number of feedback to retrieve.
     * @param after last feedback retrieve in the previous request.
     * @return {@link List<FeedbackArticle>}
     */
    override suspend fun retrieve(first: Int, after: UUID?, token: UUID): List<FeedbackArticle> =
        retrieveImpl(database = database, first = first, after = after, token = token)

    /**
     * @param token the key allowing the request to proceed.
     * @param first number of feedback to retrieve.
     * @param after last feedback retrieve in the previous request.
     * @param input the sequence of character to search in the datasource.
     * @return {@link List<FeedbackArticle>}
     */
    override suspend fun search(input: String, first: Int, after: UUID?, token: UUID): List<FeedbackArticle> =
        retrieveImpl(database = database, first = first, after = after, input = input, token = token)

    /**
     * @param token the key allowing the request to proceed.
     * @param id, unique reference of the feedback to get.
     * @return {@link List<FeedbackArticle>}
     */
    override suspend fun retrieveById(id: UUID, token: UUID): Optional<FeedbackArticle> =
        retrieveByIdImpl(id = id, token = token, database = database)
}