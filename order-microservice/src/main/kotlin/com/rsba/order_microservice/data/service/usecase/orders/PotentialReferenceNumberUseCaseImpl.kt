package com.rsba.order_microservice.data.service.usecase.orders

import com.rsba.order_microservice.data.service.usecase.queries.OrderQueries
import com.rsba.order_microservice.domain.queries.QueryCursor
import com.rsba.order_microservice.domain.usecase.custom.order.PotentialReferenceNumberUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import java.util.*

@Component
@OptIn(ExperimentalSerializationApi::class)
class PotentialReferenceNumberUseCaseImpl : PotentialReferenceNumberUseCase {

    override suspend fun invoke(database: DatabaseClient, companyId: UUID, token: UUID): String =
        database.sql(OrderQueries.potentialReferenceNumber(companyId = companyId, token = token))
            .map { row -> QueryCursor.countAsString(row = row) }
            .first()
            .map { it!! }
            .onErrorResume { throw it }
            .log()
            .awaitFirstOrElse { "0000" }
}