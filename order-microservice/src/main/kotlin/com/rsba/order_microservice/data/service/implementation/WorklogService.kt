package  com.rsba.order_microservice.data.service.implementation

import com.rsba.order_microservice.database.*
import com.rsba.order_microservice.domain.model.*
import com.rsba.order_microservice.domain.repository.WorklogRepository
import kotlinx.coroutines.reactive.awaitFirstOrElse
import mu.KLogger
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.util.*
import java.util.stream.Collectors

@Service
class WorklogService(private val logger: KLogger, private val database: DatabaseClient) : WorklogRepository {

    override suspend fun myActor(ids: Set<UUID>, userId: UUID, first: Int, after: UUID?): Map<UUID, Optional<User>> =
        Flux.fromIterable(ids)
            .flatMap { id ->
                return@flatMap database.sql(WorklogDBQueries.myActor(input = id, token = UUID.randomUUID()))
                    .map { row -> UserDBHandler.one(row = row) }
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
                logger.warn { "+WorklogService -> myActor -> error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { emptyMap() }

    override suspend fun retrieveWorklogsByTaskId(taskId: UUID, first: Int, after: UUID?, token: UUID): List<Worklog> =
        database.sql(
            WorklogDBQueries.retrieveWorklogByTaskId(
                taskId = taskId,
                first = first,
                after = after,
                token = token
            )
        ).map { row -> WorklogDBHandler.all(row = row) }
            .first()
            .onErrorResume {
                logger.warn { "+WorklogService -> retrieveWorklogsByTaskId -> error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { emptyList() }

}