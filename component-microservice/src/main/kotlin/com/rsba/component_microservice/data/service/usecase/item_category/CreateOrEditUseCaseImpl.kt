package com.rsba.component_microservice.data.service.usecase.item_category

import com.rsba.component_microservice.data.dao.ItemCategoryDao
import com.rsba.component_microservice.data.service.usecase.queries.ItemCategoryQueries
import com.rsba.component_microservice.domain.format.QueryCursor
import com.rsba.component_microservice.domain.input.ItemCategoryInput
import com.rsba.component_microservice.domain.model.ItemCategory
import com.rsba.component_microservice.domain.usecase.common.CreateOrEditUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import java.util.*

@Component
@OptIn(ExperimentalSerializationApi::class)
class CreateOrEditUseCaseImpl : CreateOrEditUseCase<ItemCategoryInput, ItemCategory> {
    override suspend fun invoke(
        database: DatabaseClient,
        input: ItemCategoryInput,
        token: UUID
    ): Optional<ItemCategory> = database.sql(ItemCategoryQueries.createOrEdit(input = input, token = token))
        .map { row -> QueryCursor.one(row = row) }
        .first()
        .map { Optional.ofNullable((it as? ItemCategoryDao?)?.to) }
        .onErrorResume { throw it }
        .log()
        .awaitFirstOrElse { Optional.empty() }
}