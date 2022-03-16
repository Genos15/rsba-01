package com.rsba.tasks_microservice.data.service.usecase.comment

import com.rsba.tasks_microservice.data.dao.CommentDao
import com.rsba.tasks_microservice.domain.model.Comment
import com.rsba.tasks_microservice.domain.queries.QueryCursor
import com.rsba.tasks_microservice.domain.queries.IQueryGuesser
import com.rsba.tasks_microservice.domain.queries.query
import com.rsba.tasks_microservice.domain.usecase.common.DeleteUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import java.util.*

@Component(value = "delete_comment")
@OptIn(ExperimentalSerializationApi::class)
class DeleteUseCaseImpl(private val database: DatabaseClient) : DeleteUseCase<Comment>, IQueryGuesser {
    override suspend fun invoke(database: DatabaseClient, input: UUID, token: UUID): Boolean = false

    override suspend fun invoke(input: UUID, token: UUID): Boolean =
        database.sql(query<CommentDao>().delete(input = input, token = token))
            .map { row -> QueryCursor.isTrue(row = row) }
            .first()
            .onErrorResume { throw it }
            .log()
            .awaitFirstOrElse { false }
}