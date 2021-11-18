package com.rsba.order_microservice.data.service.usecase.orders

import com.rsba.order_microservice.data.dao.OrderDao
import com.rsba.order_microservice.domain.model.AbstractLayer
import com.rsba.order_microservice.domain.model.AbstractStatus
import com.rsba.order_microservice.domain.model.Order
import com.rsba.order_microservice.domain.queries.IQueryGuesser
import com.rsba.order_microservice.domain.queries.QueryCursor
import com.rsba.order_microservice.domain.queries.query
import com.rsba.order_microservice.domain.usecase.common.SearchUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import java.util.*

@OptIn(ExperimentalSerializationApi::class)
@Component(value = "search_order")
class SearchUseCaseImpl : SearchUseCase<Order>, IQueryGuesser {
    override suspend fun invoke(
        database: DatabaseClient,
        input: String,
        first: Int,
        after: UUID?,
        layer: AbstractLayer?,
        status: AbstractStatus?,
        token: UUID
    ): List<Order> =
        database.sql(
            query<OrderDao>().search(
                input = input,
                token = token,
                first = first,
                after = after,
                layer = layer,
                status = status
            )
        ).map { row -> QueryCursor.all(row = row) }
            .first()
            .map { it?.mapNotNull { element -> (element as? OrderDao?)?.to } ?: emptyList() }
            .onErrorResume {
                throw it
            }
            .awaitFirstOrElse { emptyList() }
}