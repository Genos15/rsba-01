package com.rsba.order_microservice.data.service.usecase.orders

import com.rsba.order_microservice.data.dao.OrderDao
import com.rsba.order_microservice.domain.model.AbstractStatus
import com.rsba.order_microservice.domain.queries.IQueryGuesser
import com.rsba.order_microservice.domain.queries.QueryCursor
import com.rsba.order_microservice.domain.queries.query
import com.rsba.order_microservice.domain.usecase.common.CountUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import java.util.*

@OptIn(ExperimentalSerializationApi::class)
@Component(value = "count_order")
class CountUseCaseImpl : CountUseCase, IQueryGuesser {
    override suspend fun invoke(database: DatabaseClient, status: AbstractStatus?, token: UUID): Int =
        database.sql(query<OrderDao>().count(token = token, status = status))
            .map { row -> QueryCursor.count(row = row) }
            .first()
            .onErrorResume { throw it }
            .log()
            .awaitFirstOrElse { 0 }
}