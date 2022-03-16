package com.rsba.tasks_microservice.data.service.usecase.comment

import com.rsba.tasks_microservice.data.dao.CommentDao
 import com.rsba.tasks_microservice.data.service.usecase.queries.CommentQueries
import com.rsba.tasks_microservice.domain.input.CommentInput
import com.rsba.tasks_microservice.domain.queries.QueryCursor
 import com.rsba.tasks_microservice.domain.model.Comment
import com.rsba.tasks_microservice.domain.model.CommentLayer
import com.rsba.tasks_microservice.domain.queries.IQueryGuesser
import com.rsba.tasks_microservice.domain.usecase.common.custom.comment.CreateOrEditCommentUseCase
 import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import java.util.*

@Component(value = "create_edit_comment")
@OptIn(ExperimentalSerializationApi::class)
class CreateOrEditUseCaseImpl(private val database: DatabaseClient) : CreateOrEditCommentUseCase, IQueryGuesser {
    override suspend fun invoke(input: CommentInput, layer: CommentLayer?, token: UUID): Optional<Comment> =
        database.sql(CommentQueries.createOrEdit2(input = input, token = token, layer = layer))
            .map { row -> QueryCursor.one(row = row) }
            .first()
            .map { Optional.ofNullable((it.orElseGet { null } as? CommentDao?)?.to) }
            .onErrorResume { throw it }
            .log()
            .awaitFirstOrElse { Optional.empty() }

}