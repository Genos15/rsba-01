package com.rsba.order_microservice.data.service.implementation.orders

import com.rsba.order_microservice.database.OrderDBHandler
import com.rsba.order_microservice.database.OrderDBQueries
import com.rsba.order_microservice.domain.model.Order
import com.rsba.order_microservice.domain.model.OrderLevel
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface RetrieveOrderImpl {

    suspend fun completedOrderFn(first: Int, after: UUID?, token: UUID, database: DatabaseClient): List<Order> =
        database.sql(OrderDBQueries.completedOrder(token = token, first = first, after = after))
            .map { row -> OrderDBHandler.all(row = row) }
            .first()
            .onErrorResume {
                println { "completedOrderFn = ${it.message}" }
                throw it
            }
            .log()
            .awaitFirst()

    suspend fun orderByDepartmentIdFn(
        departmentId: UUID,
        first: Int,
        after: UUID?,
        token: UUID,
        database: DatabaseClient,
        level: OrderLevel?
    ): List<Order> =
        database.sql(
            OrderDBQueries.ordersByDepartmentId(
                token = token,
                first = first,
                after = after,
                departmentId = departmentId,
                level = level
            )
        ).map { row -> OrderDBHandler.all(row = row) }
            .first()
            .onErrorResume {
                println { "orderByDepartmentIdFn = ${it.message}" }
                throw it
            }
            .log()
            .awaitFirst()
}