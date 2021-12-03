package com.rsba.order_microservice.data.service.usecase.orders

import com.rsba.order_microservice.data.dao.OrderCompletionLineDao
import com.rsba.order_microservice.data.service.usecase.queries.OrderQueries
import com.rsba.order_microservice.domain.model.OrderCompletionLine
import com.rsba.order_microservice.domain.queries.QueryCursor
import com.rsba.order_microservice.domain.usecase.custom.order.RetrieveOrderCompletionLineGraphUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import java.util.*

@Component
@OptIn(ExperimentalSerializationApi::class)
class RetrieveOrderCompletionLineGraphUseCaseImpl : RetrieveOrderCompletionLineGraphUseCase {

    override suspend fun invoke(database: DatabaseClient, year: Int, token: UUID): Optional<OrderCompletionLine> =
        database.sql(OrderQueries.completionLineGraph(year = year, token = token))
            .map { row -> QueryCursor.one(row = row) }
            .first()
            .map { Optional.ofNullable((it.orElseGet { null } as? OrderCompletionLineDao?)?.to) }
            .onErrorResume { throw it }
            .log()
            .awaitFirstOrElse { Optional.empty() }
}