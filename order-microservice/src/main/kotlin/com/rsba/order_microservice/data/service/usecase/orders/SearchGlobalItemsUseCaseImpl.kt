package com.rsba.order_microservice.data.service.usecase.orders

import com.rsba.order_microservice.data.dao.ItemDao
import com.rsba.order_microservice.data.service.usecase.queries.OrderQueries
import com.rsba.order_microservice.domain.model.Item
import com.rsba.order_microservice.domain.model.OrderSearchInputValue
import com.rsba.order_microservice.domain.queries.QueryCursor
import com.rsba.order_microservice.domain.usecase.custom.order.SearchGlobalItemsUseCase
import com.rsba.order_microservice.resolver.query.GenericRetrieveConnection
import graphql.relay.Connection
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import java.util.*
import kotlinx.coroutines.reactive.awaitFirstOrElse
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers


@Component
@OptIn(ExperimentalSerializationApi::class)
class SearchGlobalItemsUseCaseImpl : SearchGlobalItemsUseCase, GenericRetrieveConnection {

    override suspend fun invoke(
        database: DatabaseClient,
        ids: Set<OrderSearchInputValue>,
        token: UUID
    ): Map<OrderSearchInputValue, Connection<Item>> =
        Flux.fromIterable(ids)
            .parallel()
            .flatMap { id ->
                database.sql(
                    OrderQueries.searchItems(input = id)
                ).map { row -> QueryCursor.all(row = row) }
                    .first()
                    .map { it?.mapNotNull { element -> (element as? ItemDao?)?.to } ?: emptyList() }
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