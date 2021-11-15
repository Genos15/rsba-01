package com.rsba.component_microservice.data.service.usecase.item_category

import com.rsba.component_microservice.data.dao.ItemCategoryDao
import com.rsba.component_microservice.data.service.usecase.queries.ItemCategoryQueries
import com.rsba.component_microservice.domain.queries.QueryCursor
import com.rsba.component_microservice.domain.input.ItemCategoryInput
import com.rsba.component_microservice.domain.model.Edition
import com.rsba.component_microservice.domain.model.EditionCase
import com.rsba.component_microservice.domain.model.MutationAction
import com.rsba.component_microservice.domain.model.ItemCategory
import com.rsba.component_microservice.domain.usecase.common.CreateOrEditUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import java.util.*

@Component(value = "create_edit_item_category")
@OptIn(ExperimentalSerializationApi::class)
class CreateOrEditUseCaseImpl : CreateOrEditUseCase<ItemCategoryInput, ItemCategory> {
    override suspend fun invoke(
        database: DatabaseClient,
        input: ItemCategoryInput,
        token: UUID,
        action: MutationAction?,
        case: Edition?,
    ): Optional<ItemCategory> =
        database.sql(ItemCategoryQueries.createOrEdit(input = input, token = token, action = action, case = case))
            .map { row -> QueryCursor.one(row = row) }
            .first()
            .map { Optional.ofNullable((it as? ItemCategoryDao?)?.to) }
            .onErrorResume { throw it }
            .log()
            .awaitFirstOrElse { Optional.empty() }
}