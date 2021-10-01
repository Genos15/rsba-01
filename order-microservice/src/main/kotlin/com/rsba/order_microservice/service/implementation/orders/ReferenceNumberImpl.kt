package com.rsba.order_microservice.service.implementation.orders

import com.rsba.order_microservice.database.OrderDBHandler
import com.rsba.order_microservice.database.OrderDBQueries
import com.rsba.order_microservice.exception.CustomGraphQLError
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.r2dbc.core.DatabaseClient
import reactor.core.publisher.SynchronousSink
import java.util.*

interface ReferenceNumberImpl {

    suspend fun retrieveNextReferenceImpl(companyId: UUID, token: UUID, database: DatabaseClient): String =
        database.sql(OrderDBQueries.retrieveNextOrderReference(token = token, companyId = companyId))
            .map { row -> OrderDBHandler.countAsString(row = row) }
            .first()
            .handle { single: String?, sink: SynchronousSink<String> ->
                if (single != null) {
                    sink.next(single)
                } else {
                    sink.error(CustomGraphQLError(message = "IMPOSSIBLE TO RETRIEVE THE NEXT ORDER REFERENCE. PLEASE CONTACT THE SUPPORT"))
                }
            }
            .log()
            .awaitFirst()
}