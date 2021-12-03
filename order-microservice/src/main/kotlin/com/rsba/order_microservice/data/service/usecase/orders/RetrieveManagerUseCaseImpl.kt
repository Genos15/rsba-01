package com.rsba.order_microservice.data.service.usecase.orders

import com.rsba.order_microservice.data.dao.AgentDao
import com.rsba.order_microservice.data.service.usecase.queries.OrderQueries
import com.rsba.order_microservice.domain.model.Agent
import com.rsba.order_microservice.domain.queries.QueryCursor
import com.rsba.order_microservice.domain.usecase.custom.order.RetrieveManagerUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.util.*

@Component
@OptIn(ExperimentalSerializationApi::class)
class RetrieveManagerUseCaseImpl : RetrieveManagerUseCase {

    override suspend fun invoke(
        database: DatabaseClient,
        ids: Set<UUID>,
        token: UUID
    ): Map<UUID, Optional<Agent>> = Flux.fromIterable(ids)
        .parallel()
        .flatMap { id ->
            database.sql(OrderQueries.manager(token = token, id = id))
                .map { row -> QueryCursor.one(row = row) }
                .first()
                .map { Optional.ofNullable((it.orElseGet { null } as? AgentDao?)?.to) }
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