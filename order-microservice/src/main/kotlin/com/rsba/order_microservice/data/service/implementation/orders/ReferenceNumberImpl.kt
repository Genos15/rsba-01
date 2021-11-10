package com.rsba.order_microservice.data.service.implementation.orders

import com.rsba.order_microservice.database.OrderDBHandler
import com.rsba.order_microservice.database.OrderDBQueries
import com.rsba.order_microservice.domain.exception.CustomGraphQLError
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface ReferenceNumberImpl {

    suspend fun retrieveNextReferenceImpl(companyId: UUID, token: UUID, database: DatabaseClient): String =
        database.sql(OrderDBQueries.retrieveNextOrderReference(token = token, companyId = companyId))
            .map { row -> OrderDBHandler.countAsString(row = row) }
            .first()
            .map { it!! }
            .onErrorResume {
                println { "retrieveNextReferenceImpl = ${it.message}" }
                throw CustomGraphQLError(message = "НЕВОЗМОЖНО ПОЛУЧИТЬ ССЫЛКУ НА СЛЕДУЮЩИЙ ЗАКАЗ. ПОЖАЛУЙСТА, СВЯЖИТЕСЬ СО СЛУЖБОЙ ПОДДЕРЖКИ")
            }
            .log()
            .awaitFirst()
}