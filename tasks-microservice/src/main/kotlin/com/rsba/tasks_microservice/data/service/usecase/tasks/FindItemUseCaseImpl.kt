package com.rsba.tasks_microservice.data.service.usecase.tasks

import com.rsba.tasks_microservice.data.dao.ItemDao
 import com.rsba.tasks_microservice.data.service.usecase.queries.TaskQueries
import com.rsba.tasks_microservice.domain.model.Item
 import com.rsba.tasks_microservice.domain.queries.QueryCursor
import com.rsba.tasks_microservice.domain.usecase.custom.task.FindItemUseCase
 import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.util.*

@Component
@OptIn(ExperimentalSerializationApi::class)
class FindItemUseCaseImpl : FindItemUseCase {
    override suspend fun invoke(database: DatabaseClient, ids: Set<UUID>, token: UUID): Map<UUID, Optional<Item>> =
        Flux.fromIterable(ids)
            .parallel()
            .flatMap { id ->
                database.sql(TaskQueries.item(token = token, id = id))
                    .map { row -> QueryCursor.one(row = row) }
                    .first()
                    .map { Optional.ofNullable((it.orElseGet { null } as? ItemDao?)?.to) }
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