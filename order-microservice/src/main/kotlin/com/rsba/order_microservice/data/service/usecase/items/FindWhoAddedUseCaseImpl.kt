package com.rsba.order_microservice.data.service.usecase.items

import com.rsba.order_microservice.data.dao.UserDao
import com.rsba.order_microservice.data.service.usecase.queries.ItemQueries
import com.rsba.order_microservice.domain.model.User
import com.rsba.order_microservice.domain.queries.QueryCursor
import com.rsba.order_microservice.domain.usecase.custom.item.FindWhoAddedUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.util.*

@Component
@OptIn(ExperimentalSerializationApi::class)
class FindWhoAddedUseCaseImpl : FindWhoAddedUseCase {

    override suspend fun invoke(
        database: DatabaseClient,
        ids: Set<UUID>,
        token: UUID
    ): Map<UUID, Optional<User>> = Flux.fromIterable(ids)
        .parallel()
        .flatMap { id ->
            database.sql(ItemQueries.whoAdded(token = token, id = id))
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