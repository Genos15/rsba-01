package com.rsba.order_microservice.data.service.implementation.order_type

import com.rsba.order_microservice.database.OrderTypeDBHandler
import com.rsba.order_microservice.database.OrderTypeQueries
import com.rsba.order_microservice.domain.model.OrderType
import kotlinx.coroutines.reactive.awaitFirstOrElse
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface RetrieveOrderTypeImpl {

    suspend fun retrieveImpl(
        database: DatabaseClient,
        input: String? = null,
        first: Int,
        after: UUID? = null,
        token: UUID
    ): List<OrderType> = database.sql(
        input?.let {
            OrderTypeQueries.search(input = input, token = token, first = first, after = after)
        } ?: OrderTypeQueries.retrieve(token = token, first = first, after = after)
    ).map { row -> OrderTypeDBHandler.all(row = row) }.first()
        .onErrorResume {
            println("RetrieveOrderTypeImpl->retrieve->error=${it.message}")
            throw it
        }
        .log()
        .awaitFirstOrElse { emptyList() }

    suspend fun retrieveByIdImpl(
        database: DatabaseClient,
        id: UUID,
        token: UUID
    ): Optional<OrderType> = database.sql(OrderTypeQueries.retrieveById(id = id, token = token))
        .map { row -> OrderTypeDBHandler.one(row = row) }.first()
        .onErrorResume {
            println("RetrieveFeedbackArticleImpl->retrieveById->error=${it.message}")
            throw it
        }
        .log()
        .awaitFirstOrElse { Optional.empty() }
}