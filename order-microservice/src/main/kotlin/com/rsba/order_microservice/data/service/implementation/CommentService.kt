package  com.rsba.order_microservice.data.service.implementation

import com.rsba.order_microservice.database.*
import com.rsba.order_microservice.domain.input.CommentTaskInput
import com.rsba.order_microservice.domain.model.*
import com.rsba.order_microservice.domain.repository.CommentRepository
import kotlinx.coroutines.reactive.awaitFirstOrElse
import mu.KLogger
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.util.*
import java.util.stream.Collectors

@Service
class CommentService(private val logger: KLogger, private val database: DatabaseClient) : CommentRepository {

    override suspend fun createOrEditComment(input: CommentTaskInput, token: UUID): Optional<Comment> =
        database.sql(CommentDBQueries.createOrEditCommentInTaskQuery(input = input, token = token))
            .map { row, meta -> CommentDBHandler.one(row = row, meta = meta) }
            .first()
            .onErrorResume {
                logger.warn { "+CommentService -> createOrEditComment -> error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { Optional.empty() }

    override suspend fun deleteComment(input: UUID, token: UUID): Boolean =
        database.sql(CommentDBQueries.deleteComment(input = input, token = token))
            .map { row, meta -> CommentDBHandler.count(row = row, meta = meta) }
            .first()
            .map { it > 0 }
            .onErrorResume {
                logger.warn { "+CommentService -> deleteComment -> error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { false }

    override suspend fun retrieveCommentsByTaskId(taskId: UUID, first: Int, after: UUID?, token: UUID): List<Comment> =
        database.sql(
            CommentDBQueries.retrieveCommentByTaskId(
                taskId = taskId,
                first = first,
                after = after,
                token = token
            )
        ).map { row, meta -> CommentDBHandler.all(row = row, meta = meta) }
            .first()
            .onErrorResume {
                logger.warn { "+CommentService -> retrieveCommentsByTaskId -> error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { emptyList() }

    override suspend fun myActor(ids: Set<UUID>, userId: UUID, first: Int, after: UUID?): Map<UUID, Optional<User>> =
        Flux.fromIterable(ids)
            .flatMap { id ->
                return@flatMap database.sql(
                    CommentDBQueries.retrieveActorByCommentId(
                        commentId = id,
                        token = UUID.randomUUID()
                    )
                ).map { row, meta -> UserDBHandler.one(row = row, meta = meta) }
                    .first()
                    .map { AbstractMap.SimpleEntry(id, it) }
            }
            .collect(Collectors.toList())
            .map {
                val map = mutableMapOf<UUID, Optional<User>>()
                it.forEach { element -> map[element.key] = element.value }
                return@map map.toMap()
            }
            .onErrorResume {
                logger.warn { "+CommentService -> myActor -> error = ${it.message}" }
                throw it
            }.awaitFirstOrElse { emptyMap() }
}