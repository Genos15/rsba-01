package com.rsba.order_microservice.data.service.usecase.orders

import com.rsba.order_microservice.data.dao.TaskDao
import com.rsba.order_microservice.data.service.usecase.queries.OrderQueries
import com.rsba.order_microservice.domain.model.OrderSearchInputValue
import com.rsba.order_microservice.domain.model.Task
import com.rsba.order_microservice.domain.queries.QueryCursor
import com.rsba.order_microservice.domain.usecase.custom.order.SearchGlobalTasksUseCase
import com.rsba.order_microservice.resolver.query.GenericRetrieveConnection
import graphql.relay.Connection
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.util.*

@Component
@OptIn(ExperimentalSerializationApi::class)
class SearchGlobalTasksUseCaseImpl : SearchGlobalTasksUseCase, GenericRetrieveConnection {

    override suspend fun invoke(
        database: DatabaseClient,
        ids: Set<OrderSearchInputValue>,
        token: UUID
    ): Map<OrderSearchInputValue, Connection<Task>> =
        Flux.fromIterable(ids)
            .parallel()
            .flatMap { id ->
                database.sql(
                    OrderQueries.searchTasks(input = id)
                ).map { row -> QueryCursor.all(row = row) }
                    .first()
                    .map { it?.mapNotNull { element -> (element as? TaskDao?)?.to } ?: emptyList() }
                    .map { AbstractMap.SimpleEntry(id, it) }
                    .onErrorResume { throw it }
            }
            .runOn(Schedulers.parallel())
            .sequential()
            .collectList()
            .map { entries ->
                entries.associateBy(
                    { it.key },
                    {
                        perform(
                            entries = (it.value ?: emptyList()),
                            first = ids.first().first,
                            after = ids.first().after
                        )
                    })
            }
            .onErrorResume { throw it }
            .log()
            .awaitFirstOrElse { emptyMap() }
}