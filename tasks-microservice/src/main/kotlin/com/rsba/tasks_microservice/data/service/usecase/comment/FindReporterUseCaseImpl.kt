package com.rsba.tasks_microservice.data.service.usecase.comment

import com.rsba.tasks_microservice.data.dao.UserDao
import com.rsba.tasks_microservice.data.service.usecase.queries.CommentQueries
import com.rsba.tasks_microservice.domain.model.User
import com.rsba.tasks_microservice.domain.queries.QueryCursor
import com.rsba.tasks_microservice.domain.usecase.common.custom.comment.FindReporterUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.util.*

@Component
@OptIn(ExperimentalSerializationApi::class)
class FindReporterUseCaseImpl(private val database: DatabaseClient) : FindReporterUseCase {
    override suspend fun invoke(
        ids: Set<UUID>,
        token: UUID
    ): Map<UUID, Optional<User>> =
        Flux.fromIterable(ids)
            .parallel()
            .flatMap { id ->
                database.sql(CommentQueries.reporter(token = token, id = id))
                    .map { row -> QueryCursor.one(row = row) }
                    .first()
                    .map { Optional.ofNullable((it.orElseGet { null } as? UserDao?)?.to) }
                    .map { AbstractMap.SimpleEntry(id, it) }
                    .onErrorResume { throw it }
            }
            .runOn(Schedulers.parallel())
            .sequential()
            .collectList()
            .map { entries -> entries.associateBy({ it.key }, { it.value }) }
            .onErrorResume { throw it }
            .log()
            .awaitFirstOrElse { emptyMap() }
}