package com.rsba.component_microservice.data.service.usecase.items

import com.rsba.component_microservice.data.dao.InformationUsageDao
import com.rsba.component_microservice.data.service.usecase.queries.ItemQueries
import com.rsba.component_microservice.domain.exception.CustomGraphQLError
import com.rsba.component_microservice.domain.model.InformationUsage
import com.rsba.component_microservice.domain.queries.QueryCursor
import com.rsba.component_microservice.domain.usecase.custom.item.FindInformationUsageUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.time.OffsetDateTime
import java.util.*

@Component(value = "find_item_information_usage")
@OptIn(ExperimentalSerializationApi::class)
class FindInformationUsageUseCaseImpl(private val database: DatabaseClient) : FindInformationUsageUseCase {
    override suspend fun invoke(
        input: UUID,
        from: OffsetDateTime?,
        to: OffsetDateTime?,
        token: UUID
    ): Optional<InformationUsage> =
        database.sql(ItemQueries.usage(input = input, token = token, from = from, to = to))
            .map { row -> QueryCursor.one(row = row) }
            .first()
            .map { Optional.ofNullable((it as? InformationUsageDao?)?.to) }
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