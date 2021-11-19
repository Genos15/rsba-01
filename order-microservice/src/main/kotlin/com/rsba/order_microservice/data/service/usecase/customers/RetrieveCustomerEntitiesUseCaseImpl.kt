package com.rsba.order_microservice.data.service.usecase.customers

import com.rsba.order_microservice.data.dao.CustomerDao
import com.rsba.order_microservice.data.service.usecase.queries.CustomerQueries
import com.rsba.order_microservice.domain.model.Customer
import com.rsba.order_microservice.domain.queries.QueryCursor
import com.rsba.order_microservice.domain.usecase.custom.customer.RetrieveCustomerEntitiesUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.util.*

@Component
@OptIn(ExperimentalSerializationApi::class)
class RetrieveCustomerEntitiesUseCaseImpl : RetrieveCustomerEntitiesUseCase {

    override suspend fun invoke(
        database: DatabaseClient,
        ids: Set<UUID>,
        first: Int,
        after: UUID?,
        token: UUID
    ): Map<UUID, List<Customer>> =
        Flux.fromIterable(ids)
            .parallel()
            .flatMap { id ->
                database.sql(CustomerQueries.entities(token = token, id = id, first = first, after = after))
                    .map { row -> QueryCursor.all(row = row) }
                    .first()
                    .map { it?.mapNotNull { element -> (element as? CustomerDao?)?.to } ?: emptyList() }
                    .map { AbstractMap.SimpleEntry(id, it) }
                    .onErrorResume { throw it }
            }
            .runOn(Schedulers.parallel())
            .sequential()
            .collectList()
            .map { entries -> entries.associateBy({ it.key }, { it.value ?: emptyList() }) }
            .onErrorResume { throw it }
            .log()
            .awaitFirstOrElse { emptyMap() }
}