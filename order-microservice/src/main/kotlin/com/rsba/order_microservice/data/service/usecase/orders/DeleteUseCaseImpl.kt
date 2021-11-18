package com.rsba.order_microservice.data.service.usecase.orders

import com.rsba.order_microservice.data.dao.OrderDao
import com.rsba.order_microservice.domain.model.Order
import com.rsba.order_microservice.domain.queries.IQueryGuesser
import com.rsba.order_microservice.domain.queries.QueryCursor
import com.rsba.order_microservice.domain.queries.query
import com.rsba.order_microservice.domain.usecase.common.DeleteUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import java.util.*

@Component(value = "delete_order")
@OptIn(ExperimentalSerializationApi::class)
class DeleteUseCaseImpl : DeleteUseCase<Order>, IQueryGuesser {
    override suspend fun invoke(database: DatabaseClient, input: UUID, token: UUID): Boolean =
        database.sql(query<OrderDao>().delete(input = input, token = token))
            .map { row -> QueryCursor.isTrue(row = row) }
            .first()
            .onErrorResume { throw it }
            .log()
            .awaitFirstOrElse { false }
}