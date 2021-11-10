package com.rsba.order_microservice.data.service.implementation.order_type


import com.rsba.order_microservice.database.OrderTypeDBHandler
import com.rsba.order_microservice.database.OrderTypeQueries
import com.rsba.order_microservice.domain.input.OrderTypeInput
import com.rsba.order_microservice.domain.model.OrderType
import kotlinx.coroutines.reactive.awaitFirstOrElse
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface EditOrderTypeImpl {

    suspend fun createOrEditImpl(
        database: DatabaseClient,
        input: OrderTypeInput,
        token: UUID
    ): Optional<OrderType> = database.sql(OrderTypeQueries.createOrEdit(input = input, token = token))
        .map { row -> OrderTypeDBHandler.one(row = row) }.first()
        .onErrorResume {
            println("EditOrderTypeImpl->createOrEdit->error=${it.message}")
            throw it
        }
        .log()
        .awaitFirstOrElse { Optional.empty() }


    suspend fun deleteImpl(
        database: DatabaseClient,
        input: UUID,
        token: UUID
    ): Boolean = database.sql(OrderTypeQueries.delete(input = input, token = token))
        .map { row -> OrderTypeDBHandler.count(row = row) }.first()
        .map { it != null && it > 0 }
        .onErrorResume {
            println("EditOrderTypeImpl->deleteImpl->error=${it.message}")
            throw it
        }
        .log()
        .awaitFirstOrElse { false }

}