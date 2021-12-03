package com.rsba.order_microservice.data.service.usecase.order_types

import com.rsba.order_microservice.data.dao.OrderTypeDao
import com.rsba.order_microservice.domain.input.OrderTypeInput
import com.rsba.order_microservice.domain.model.Edition
import com.rsba.order_microservice.domain.model.MutationAction
import com.rsba.order_microservice.domain.model.OrderType
import com.rsba.order_microservice.domain.queries.IQueryGuesser
import com.rsba.order_microservice.domain.queries.QueryCursor
import com.rsba.order_microservice.domain.queries.query
import com.rsba.order_microservice.domain.usecase.common.CreateOrEditUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import java.util.*

@Component(value = "create_edit_order_type")
@OptIn(ExperimentalSerializationApi::class)
class CreateOrEditUseCaseImpl : CreateOrEditUseCase<OrderTypeInput, OrderType>, IQueryGuesser {
    override suspend fun invoke(
        database: DatabaseClient,
        input: OrderTypeInput,
        token: UUID,
        action: MutationAction?,
        case: Edition?,
    ): Optional<OrderType> =
        database.sql(query<OrderTypeDao>().createOrEdit(input = input, token = token, action = action, case = case))
            .map { row -> QueryCursor.one(row = row) }
            .first()
            .map { Optional.ofNullable((it.orElseGet { null } as? OrderTypeDao?)?.to) }
            .onErrorResume { throw it }
            .log()
            .awaitFirstOrElse { Optional.empty() }
}