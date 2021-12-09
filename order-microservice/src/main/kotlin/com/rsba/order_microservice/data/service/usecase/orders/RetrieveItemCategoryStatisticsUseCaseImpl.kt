package com.rsba.order_microservice.data.service.usecase.orders

 import com.rsba.order_microservice.data.dao.ItemCategoryStatisticsDao
import com.rsba.order_microservice.data.service.usecase.queries.OrderQueries
 import com.rsba.order_microservice.domain.model.ItemCategoryStatistics
import com.rsba.order_microservice.domain.queries.QueryCursor
 import com.rsba.order_microservice.domain.usecase.custom.order.RetrieveItemCategoryStatisticsUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.util.*

@Component
@OptIn(ExperimentalSerializationApi::class)
class RetrieveItemCategoryStatisticsUseCaseImpl : RetrieveItemCategoryStatisticsUseCase {

    override suspend fun invoke(
        database: DatabaseClient,
        ids: Set<UUID>,
        first: Int,
        after: UUID?,
        token: UUID
    ): Map<UUID, List<ItemCategoryStatistics>> =
        Flux.fromIterable(ids)
            .parallel()
            .flatMap { id ->
                database.sql(
                    OrderQueries.itemCategoryStatistics(
                        token = token,
                        id = id,
                        first = first,
                        after = after,
                    )
                ).map { row -> QueryCursor.all(row = row) }
                    .first()
                    .map { it?.mapNotNull { element -> (element as? ItemCategoryStatisticsDao?)?.to } ?: emptyList() }
                    .map { AbstractMap.SimpleEntry(id, it) }
                    .onErrorResume { throw it }
            }
            .runOn(Schedulers.parallel())
            .sequential()
            .collectList()
            .map { entries -> entries.associateBy({ it.key }, { it.value ?: emptyList() }) }
            .onErrorResume { throw it }
            .log()
            .awaitFirstOrElse { emptyMap() }
}