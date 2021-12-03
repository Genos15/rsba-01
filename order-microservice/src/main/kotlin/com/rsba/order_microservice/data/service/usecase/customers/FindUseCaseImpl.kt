package com.rsba.order_microservice.data.service.usecase.customers

import com.rsba.order_microservice.data.dao.CustomerDao
import com.rsba.order_microservice.domain.exception.CustomGraphQLError
import com.rsba.order_microservice.domain.model.Customer
import com.rsba.order_microservice.domain.queries.IQueryGuesser
import com.rsba.order_microservice.domain.queries.QueryCursor
import com.rsba.order_microservice.domain.queries.query
import com.rsba.order_microservice.domain.usecase.common.FindUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.*

@Component(value = "find_customer")
@OptIn(ExperimentalSerializationApi::class)
class FindUseCaseImpl : FindUseCase<Customer>, IQueryGuesser {
    override suspend fun invoke(database: DatabaseClient, id: UUID, token: UUID): Optional<Customer> =
        database.sql(query<CustomerDao>().find(id = id, token = token))
            .map { row -> QueryCursor.one(row = row) }
            .first()
            .map { Optional.ofNullable((it.orElseGet { null } as? CustomerDao?)?.to) }
            .onErrorResume {
                if (it is CustomGraphQLError) {
                    Mono.empty()
                } else {
                    throw it
                }
            }
            .log()
            .awaitFirstOrElse { Optional.empty() }
}