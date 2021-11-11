package com.rsba.component_microservice.data.service.usecase.item_category

import com.rsba.component_microservice.data.dao.ItemCategoryDao
import com.rsba.component_microservice.data.service.usecase.queries.ItemCategoryQueries
import com.rsba.component_microservice.domain.format.QueryCursor
import com.rsba.component_microservice.domain.model.ItemCategory
import com.rsba.component_microservice.domain.usecase.custom.item_category.RetrieveItemCategoryChildrenUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import java.util.*


@Component
@OptIn(ExperimentalSerializationApi::class)
class RetrieveItemCategoryChildrenUseCaseImpl : RetrieveItemCategoryChildrenUseCase {
    override suspend fun invoke(
        database: DatabaseClient,
        id: UUID,
        first: Int,
        after: UUID?,
        token: UUID
    ): List<ItemCategory> =
        database.sql(ItemCategoryQueries.children(token = token, first = first, after = after, id = id))
            .map { row -> QueryCursor.all(row = row) }
            .first()
            .map { it?.mapNotNull { element -> (element as? ItemCategoryDao?)?.to } ?: emptyList() }
            .onErrorResume {
                throw it
            }
            .log()
            .awaitFirstOrElse { emptyList() }
}