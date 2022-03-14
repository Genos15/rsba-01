package com.rsba.order_microservice.data.service.usecase.orders


import com.rsba.order_microservice.data.dao.WorklogDao
import com.rsba.order_microservice.data.service.usecase.queries.OrderQueries
import com.rsba.order_microservice.domain.model.Worklog
import com.rsba.order_microservice.domain.queries.QueryCursor
import com.rsba.order_microservice.domain.usecase.custom.order.RetrieveWorklogsUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.time.OffsetDateTime
import java.util.*

@Component(value = "retrieve_orders_worklogs")
@OptIn(ExperimentalSerializationApi::class)
class RetrieveWorklogsUseCaseImpl(private val database: DatabaseClient) : RetrieveWorklogsUseCase {

    override suspend fun invoke(
        ids: Set<UUID>,
        first: Int,
        after: UUID?,
        rangeStart: OffsetDateTime?,
        rangeEnd: OffsetDateTime?,
        token: UUID
    ): Map<UUID, List<Worklog>> =
        Flux.fromIterable(ids)
            .parallel()
            .flatMap { id ->
                database.sql(
                    OrderQueries.worklogs(
                        token = token,
                        id = id,
                        first = first,
                        after = after,
                        rangeStart = rangeStart,
                        rangeEnd = rangeEnd,
                    )
                ).map { row -> QueryCursor.all(row = row) }
                    .first()
                    .map { it?.mapNotNull { element -> (element as? WorklogDao?)?.to } ?: emptyList() }
                    .map { AbstractMap.SimpleEntry(id, it) }
                    .onErrorResume { throw it }
                    .log("retrieve_orders_worklogs_1")
            }
            .runOn(Schedulers.parallel())
            .sequential()
            .collectList()
            .map { entries -> entries.associateBy({ it.key }, { it.value ?: emptyList() }) }
            .onErrorResume { throw it }
            .log("retrieve_orders_worklogs")
            .awaitFirstOrElse { emptyMap() }
}