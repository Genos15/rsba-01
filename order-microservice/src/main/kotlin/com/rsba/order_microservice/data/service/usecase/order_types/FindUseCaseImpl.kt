package com.rsba.order_microservice.data.service.usecase.order_types

import com.rsba.order_microservice.data.dao.OrderTypeDao
import com.rsba.order_microservice.domain.exception.CustomGraphQLError
import com.rsba.order_microservice.domain.model.OrderType
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

@Component(value = "find_order_type")
@OptIn(ExperimentalSerializationApi::class)
class FindUseCaseImpl : FindUseCase<OrderType>, IQueryGuesser {
    override suspend fun invoke(database: DatabaseClient, id: UUID, token: UUID): Optional<OrderType> =
        database.sql(query<OrderTypeDao>().find(id = id, token = token))
            .map { row -> QueryCursor.one(row = row) }
            .first()
            .map { Optional.ofNullable((it.orElseGet { null } as? OrderTypeDao?)?.to) }
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