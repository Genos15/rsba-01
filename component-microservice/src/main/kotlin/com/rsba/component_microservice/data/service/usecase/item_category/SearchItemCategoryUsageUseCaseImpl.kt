package com.rsba.component_microservice.data.service.usecase.item_category

import com.rsba.component_microservice.data.dao.ItemCategoryUsageDao
import com.rsba.component_microservice.data.service.usecase.queries.ItemCategoryQueries
import com.rsba.component_microservice.domain.queries.QueryCursor
import com.rsba.component_microservice.domain.model.ItemCategoryUsage
import com.rsba.component_microservice.domain.usecase.custom.item_category.SearchItemCategoryUsageUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import java.time.OffsetDateTime
import java.util.*


@Component
@OptIn(ExperimentalSerializationApi::class)
class SearchItemCategoryUsageUseCaseImpl : SearchItemCategoryUsageUseCase {
    override suspend fun invoke(
        database: DatabaseClient,
        input: String,
        first: Int,
        after: UUID?,
        from: OffsetDateTime?,
        to: OffsetDateTime?,
        token: UUID
    ): List<ItemCategoryUsage> =
        database.sql(
            ItemCategoryQueries.usages(
                token = token,
                first = first,
                after = after,
                from = from,
                to = to,
                input = input
            )
        )
            .map { row -> QueryCursor.all(row = row) }
            .first()
            .map { it?.mapNotNull { element -> (element as? ItemCategoryUsageDao?)?.to } ?: emptyList() }
            .onErrorResume {
                throw it
            }
            .log()
            .awaitFirstOrElse { emptyList() }


}