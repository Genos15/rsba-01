package com.rsba.component_microservice.data.service.usecase.item_category

import com.rsba.component_microservice.data.dao.ItemCategoryUsageDao
import com.rsba.component_microservice.data.service.usecase.queries.ItemCategoryQueries
import com.rsba.component_microservice.domain.exception.CustomGraphQLError
import com.rsba.component_microservice.domain.model.ItemCategoryUsage
import com.rsba.component_microservice.domain.queries.QueryCursor
import com.rsba.component_microservice.domain.usecase.custom.item_category.FindItemCategoryUsageUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import reactor.core.publisher.Mono
import java.time.OffsetDateTime
import java.util.*

@OptIn(ExperimentalSerializationApi::class)
class FindItemCategoryUsageUseCaseImpl : FindItemCategoryUsageUseCase {
    override suspend fun invoke(
        database: DatabaseClient,
        input: UUID,
        from: OffsetDateTime?,
        to: OffsetDateTime?,
        token: UUID
    ): Optional<ItemCategoryUsage> =
        database.sql(ItemCategoryQueries.usage(input = input, token = token, from = from, to = to))
            .map { row -> QueryCursor.one(row = row) }
            .first()
            .map { Optional.ofNullable((it as? ItemCategoryUsageDao?)?.to) }
            .onErrorResume {
                if (it is CustomGraphQLError) {
                    Mono.empty()
                } else {
                    throw it
                }
            }
            .log()
            .awaitFirstOrElse { Optional.empty() }
}