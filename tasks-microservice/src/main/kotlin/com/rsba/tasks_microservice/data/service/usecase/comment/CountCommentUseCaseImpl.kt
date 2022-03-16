package com.rsba.tasks_microservice.data.service.usecase.comment

import com.rsba.tasks_microservice.data.service.usecase.queries.CommentQueries
import com.rsba.tasks_microservice.domain.model.CommentLayer
import com.rsba.tasks_microservice.domain.queries.QueryCursor
import com.rsba.tasks_microservice.domain.usecase.common.custom.comment.CountCommentUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import java.util.*

@OptIn(ExperimentalSerializationApi::class)
@Component(value = "count_comments")
class CountCommentUseCaseImpl(private val database: DatabaseClient) : CountCommentUseCase {
    override suspend fun invoke(hostId: UUID, layer: CommentLayer?, token: UUID): Int =
        database.sql(CommentQueries.count2(hostId = hostId, layer = layer, token = token))
            .map { row -> QueryCursor.count(row = row) }
            .first()
            .onErrorResume { throw it }
            .log()
            .awaitFirstOrElse { 0 }
}