package com.rsba.component_microservice.data.service.usecase.item_category

import com.rsba.component_microservice.data.dao.ItemCategoryDao
import com.rsba.component_microservice.data.service.usecase.queries.ItemCategoryQueries
import com.rsba.component_microservice.domain.queries.QueryCursor
import com.rsba.component_microservice.domain.model.ItemCategory
import com.rsba.component_microservice.domain.usecase.common.RetrieveUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import java.util.*

@Component(value = "retrieve_item_category")
@OptIn(ExperimentalSerializationApi::class)
class RetrieveUseCaseImpl : RetrieveUseCase<ItemCategory> {
    override suspend fun invoke(database: DatabaseClient, first: Int, after: UUID?, token: UUID): List<ItemCategory> =
        database.sql(ItemCategoryQueries.retrieve(token = token, first = first, after = after))
            .map { row -> QueryCursor.all(row = row) }
            .first()
            .map { it?.mapNotNull { element -> (element as? ItemCategoryDao?)?.to } ?: emptyList() }
            .onErrorResume {
                println("retrieveImpl=${it.message}")
                throw it
            }
            .awaitFirstOrElse { emptyList() }
}