package com.rsba.component_microservice.data.service.usecase.items

import com.rsba.component_microservice.data.dao.ItemDao
import com.rsba.component_microservice.data.service.usecase.queries.ItemQueries
import com.rsba.component_microservice.domain.queries.QueryCursor
import com.rsba.component_microservice.domain.input.ItemInput
import com.rsba.component_microservice.domain.model.Item
import com.rsba.component_microservice.domain.usecase.custom.item.DetachOperationToItemUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import java.util.*


@Component
@OptIn(ExperimentalSerializationApi::class)
class DetachOperationToItemUseCaseImpl : DetachOperationToItemUseCase {
    override suspend fun invoke(database: DatabaseClient, input: ItemInput, token: UUID): Optional<Item> =
        database.sql(ItemQueries.detachOperation(token = token, input = input))
            .map { row -> QueryCursor.one(row = row) }
            .first()
            .map { Optional.ofNullable((it as? ItemDao?)?.to) }
            .onErrorResume {
                throw it
            }
            .log()
            .awaitFirstOrElse { Optional.empty() }
}