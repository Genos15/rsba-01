package com.rsba.order_microservice.data.service.implementation.orders

import com.rsba.order_microservice.database.OrderDBQueries
import com.rsba.order_microservice.database.OrderTypeDBHandler
import com.rsba.order_microservice.domain.model.OrderType
import kotlinx.coroutines.reactive.awaitFirstOrElse
import org.springframework.r2dbc.core.DatabaseClient
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.util.*

interface OrderDataloaderServiceImpl {

    suspend fun myType(ids: Set<UUID>, database: DatabaseClient): Map<UUID, Optional<OrderType>> =
        Flux.fromIterable(ids)
            .parallel()
            .flatMap { id ->
                database.sql(OrderDBQueries.retrieveMyType(orderId = id, token = UUID.randomUUID()))
                    .map { row, meta -> OrderTypeDBHandler.one(row = row, meta = meta) }
                    .first()
                    .map { AbstractMap.SimpleEntry(id, it) }
            }
            .runOn(Schedulers.parallel())
            .sequential()
            .collectList()
            .map {
                val map = mutableMapOf<UUID, Optional<OrderType>>()
                it.forEach { element -> map[element.key] = element.value }
                return@map map.toMap()
            }
            .onErrorResume {
                println { "myType = ${it.message}" }
                throw it
            }
            .log()
            .awaitFirstOrElse { emptyMap() }
}