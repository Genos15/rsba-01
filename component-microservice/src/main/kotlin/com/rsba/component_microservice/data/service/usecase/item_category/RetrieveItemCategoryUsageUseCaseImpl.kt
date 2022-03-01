package com.rsba.component_microservice.data.service.usecase.item_category

import com.rsba.component_microservice.data.dao.InformationUsageDao
import com.rsba.component_microservice.data.service.usecase.queries.ItemCategoryQueries
import com.rsba.component_microservice.domain.queries.QueryCursor
import com.rsba.component_microservice.domain.model.InformationUsage
import com.rsba.component_microservice.domain.usecase.custom.item_category.RetrieveItemCategoryUsageUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import java.time.OffsetDateTime
import java.util.*


@Component
@OptIn(ExperimentalSerializationApi::class)
class RetrieveItemCategoryUsageUseCaseImpl : RetrieveItemCategoryUsageUseCase {
    override suspend fun invoke(
        database: DatabaseClient,
        first: Int,
        after: UUID?,
        from: OffsetDateTime?,
        to: OffsetDateTime?,
        token: UUID
    ): List<InformationUsage> =
        database.sql(ItemCategoryQueries.usages(token = token, first = first, after = after, from = from, to = to))
            .map { row -> QueryCursor.all(row = row) }
            .first()
            .map { it?.mapNotNull { element -> (element as? InformationUsageDao?)?.to } ?: emptyList() }
            .onErrorResume {
                throw it
            }
            .log()
            .awaitFirstOrElse { emptyList() }


}