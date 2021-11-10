package com.rsba.order_microservice.data.service.implementation.items

import com.rsba.order_microservice.database.ItemDBHandler
import com.rsba.order_microservice.database.ItemDBQueries
import com.rsba.order_microservice.domain.model.Item
import kotlinx.coroutines.reactive.awaitFirstOrElse
import org.springframework.r2dbc.core.DatabaseClient
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.util.*

interface RetrieveComponentAndItemImpl {

    suspend fun retrieveComponentInItemsFn(
        items: Set<Item>,
        token: UUID,
        database: DatabaseClient
    ): Map<Item, List<Item>> =

        Flux.fromIterable(items)
            .parallel()
            .flatMap { single ->
                return@flatMap database.sql(ItemDBQueries.retrieveComponentInItem(input = single, token = token))
                    .map { row -> ItemDBHandler.all(row = row) }
                    .first()
                    .map { AbstractMap.SimpleEntry(single, it) }
            }
            .runOn(Schedulers.parallel())
            .sequential()
            .collectList()
            .map {
                val map = mutableMapOf<Item, List<Item>>()
                it.forEach { element -> map[element.key] = element.value ?: emptyList() }
                return@map map.toMap()
            }
            .onErrorResume { throw it }
            .awaitFirstOrElse { emptyMap() }
}