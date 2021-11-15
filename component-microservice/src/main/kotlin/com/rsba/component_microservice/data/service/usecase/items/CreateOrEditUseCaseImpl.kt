package com.rsba.component_microservice.data.service.usecase.items

import com.rsba.component_microservice.data.dao.ItemDao
import com.rsba.component_microservice.data.service.usecase.queries.ItemQueries
import com.rsba.component_microservice.domain.queries.QueryCursor
import com.rsba.component_microservice.domain.input.ItemInput
import com.rsba.component_microservice.domain.model.Edition
import com.rsba.component_microservice.domain.model.EditionCase
import com.rsba.component_microservice.domain.model.MutationAction
import com.rsba.component_microservice.domain.model.Item
import com.rsba.component_microservice.domain.usecase.common.CreateOrEditUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import java.util.*

@Component(value = "create_edit_item")
@OptIn(ExperimentalSerializationApi::class)
class CreateOrEditUseCaseImpl : CreateOrEditUseCase<ItemInput, Item> {
    override suspend fun invoke(
        database: DatabaseClient,
        input: ItemInput,
        token: UUID,
        action: MutationAction?,
        case: Edition?,
    ): Optional<Item> =
        database.sql(ItemQueries.createOrEdit(input = input, token = token, action = action, case = case))
            .map { row -> QueryCursor.one(row = row) }
            .first()
            .map { Optional.ofNullable((it as? ItemDao?)?.to) }
            .onErrorResume { throw it }
            .log()
            .awaitFirstOrElse { Optional.empty() }
}