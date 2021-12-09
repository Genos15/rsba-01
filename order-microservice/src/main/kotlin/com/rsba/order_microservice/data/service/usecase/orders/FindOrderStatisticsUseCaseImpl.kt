package com.rsba.order_microservice.data.service.usecase.orders

import com.rsba.order_microservice.domain.model.OrderStatistics
import com.rsba.order_microservice.domain.usecase.custom.order.FindOrderStatisticsUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.util.*

@Component
@OptIn(ExperimentalSerializationApi::class)
class FindOrderStatisticsUseCaseImpl : FindOrderStatisticsUseCase {

    override suspend fun invoke(
        database: DatabaseClient,
        ids: Set<UUID>,
        token: UUID
    ): Map<UUID, Optional<OrderStatistics>> = Flux.fromIterable(ids)
        .parallel()
        .flatMap { id ->
            Mono.just(AbstractMap.SimpleEntry(id, Optional.of(OrderStatistics(id = id))))
        }
        .runOn(Schedulers.parallel())
        .sequential()
        .collectList()
        .map { entries -> entries.associateBy({ it.key }, { it.value }) }
        .onErrorResume { throw it }
        .log()
        .awaitFirstOrElse { emptyMap() }
}