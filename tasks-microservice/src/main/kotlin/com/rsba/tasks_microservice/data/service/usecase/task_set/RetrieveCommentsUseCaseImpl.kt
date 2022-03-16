package com.rsba.tasks_microservice.data.service.usecase.task_set


import com.rsba.tasks_microservice.data.dao.CommentDao
import com.rsba.tasks_microservice.data.service.usecase.queries.TaskSetQueries
import com.rsba.tasks_microservice.domain.model.Comment
import com.rsba.tasks_microservice.domain.model.CommentLayer
import com.rsba.tasks_microservice.domain.queries.QueryCursor
import com.rsba.tasks_microservice.domain.usecase.common.RetrieveCommentsUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.util.*

@Component(value = "retrieve_task_set_comments")
@OptIn(ExperimentalSerializationApi::class)
class RetrieveCommentsUseCaseImpl(private val database: DatabaseClient) : RetrieveCommentsUseCase {
    override suspend fun invoke(
        ids: Set<UUID>,
        first: Int,
        after: UUID?,
        layer: CommentLayer?,
        token: UUID
    ): Map<UUID, List<Comment>> =
        Flux.fromIterable(ids)
            .parallel()
            .flatMap { id ->
                database.sql(
                    TaskSetQueries.comments(
                        token = token,
                        id = id,
                        first = first,
                        after = after,
                        layer = layer,
                    )
                ).map { row -> QueryCursor.all(row = row) }
                    .first()
                    .map { it?.mapNotNull { element -> (element as? CommentDao?)?.to } ?: emptyList() }
                    .map { AbstractMap.SimpleEntry(id, it) }
                    .onErrorResume { throw it }
            }
            .runOn(Schedulers.parallel())
            .sequential()
            .collectList()
            .map { entries -> entries.associateBy({ it.key }, { it.value ?: emptyList() }) }
            .onErrorResume { throw it }
            .log()
            .awaitFirstOrElse { emptyMap() }
}