package com.rsba.order_microservice.data.service.usecase.customers

import com.rsba.order_microservice.data.dao.CustomerDao
import com.rsba.order_microservice.domain.model.AbstractStatus
import com.rsba.order_microservice.domain.model.Customer
import com.rsba.order_microservice.domain.model.OrderLayer
import com.rsba.order_microservice.domain.queries.IQueryGuesser
import com.rsba.order_microservice.domain.queries.QueryCursor
import com.rsba.order_microservice.domain.queries.query
import com.rsba.order_microservice.domain.usecase.common.RetrieveUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import java.util.*

@Component(value = "retrieve_customer")
@OptIn(ExperimentalSerializationApi::class)
class RetrieveUseCaseImpl : RetrieveUseCase<Customer>, IQueryGuesser {
    override suspend fun invoke(
        database: DatabaseClient,
        first: Int,
        after: UUID?,
        layer: OrderLayer?,
        status: AbstractStatus?,
        token: UUID
    ): List<Customer> =
        database.sql(
            query<CustomerDao>().retrieve(
                token = token,
                first = first,
                after = after,
                layer = layer,
                status = status
            )
        ).map { row -> QueryCursor.all(row = row) }
            .first()
            .map { it?.mapNotNull { element -> (element as? CustomerDao?)?.to } ?: emptyList() }
            .onErrorResume {
                throw it
            }
            .awaitFirstOrElse { emptyList() }
}