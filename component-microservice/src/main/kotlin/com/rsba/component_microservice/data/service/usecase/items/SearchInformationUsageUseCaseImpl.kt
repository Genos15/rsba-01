package com.rsba.component_microservice.data.service.usecase.items

import com.rsba.component_microservice.data.dao.InformationUsageDao
import com.rsba.component_microservice.data.service.usecase.queries.ItemQueries
import com.rsba.component_microservice.domain.queries.QueryCursor
import com.rsba.component_microservice.domain.model.InformationUsage
import com.rsba.component_microservice.domain.usecase.custom.item.SearchInformationUsageUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import java.time.OffsetDateTime
import java.util.*


@Component(value = "search_item_information_usage")
@OptIn(ExperimentalSerializationApi::class)
class SearchInformationUsageUseCaseImpl(private val database: DatabaseClient) : SearchInformationUsageUseCase {
    override suspend fun invoke(
        input: String,
        first: Int,
        after: UUID?,
        from: OffsetDateTime?,
        to: OffsetDateTime?,
        token: UUID
    ): List<InformationUsage> =
        database.sql(
            ItemQueries.usages(
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
            .map { it?.mapNotNull { element -> (element as? InformationUsageDao?)?.to } ?: emptyList() }
            .onErrorResume {
                throw it
            }
            .log()
            .awaitFirstOrElse { emptyList() }


}