package com.rsba.order_microservice.data.service.usecase.orders

import com.rsba.order_microservice.data.dao.ItemDao
import com.rsba.order_microservice.data.service.usecase.queries.OrderQueries
import com.rsba.order_microservice.domain.input.ItemInput
import com.rsba.order_microservice.domain.model.Item
import com.rsba.order_microservice.domain.model.MutationAction
import com.rsba.order_microservice.domain.queries.QueryCursor
import com.rsba.order_microservice.domain.usecase.custom.order.EditItemUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import java.util.*

@Component
@OptIn(ExperimentalSerializationApi::class)
class EditItemUseCaseImpl : EditItemUseCase {
    override suspend fun invoke(
        database: DatabaseClient,
        input: ItemInput,
        token: UUID,
        action: MutationAction?
    ): Optional<Item> =
        database.sql(OrderQueries.editItem(input = input, token = token, action = action))
            .map { row -> QueryCursor.one(row = row) }
            .first()
            .map { Optional.ofNullable((it.orElseGet { null } as? ItemDao?)?.to) }
            .onErrorResume { throw it }
            .log()
            .awaitFirstOrElse { Optional.empty() }
}