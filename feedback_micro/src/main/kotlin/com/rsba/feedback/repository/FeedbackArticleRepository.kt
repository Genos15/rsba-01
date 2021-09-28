package com.rsba.feedback.repository

import com.rsba.feedback.domain.input.*
import com.rsba.feedback.domain.model.FeedbackArticle
import java.util.*

interface FeedbackArticleRepository {
    suspend fun createOrEdit(input: FeedbackArticleInput, token: UUID): Optional<FeedbackArticle>
    suspend fun delete(input: UUID, token: UUID): Boolean
    suspend fun retrieve(first: Int, after: UUID?, token: UUID): List<FeedbackArticle>
    suspend fun search(input: String, first: Int, after: UUID?, token: UUID): List<FeedbackArticle>
    suspend fun retrieveById(id: UUID, token: UUID): Optional<FeedbackArticle>
}