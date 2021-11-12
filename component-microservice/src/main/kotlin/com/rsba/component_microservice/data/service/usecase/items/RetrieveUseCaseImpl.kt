package com.rsba.component_microservice.data.service.usecase.items

import com.rsba.component_microservice.data.dao.ItemDao
import com.rsba.component_microservice.data.service.usecase.queries.ItemQueries
import com.rsba.component_microservice.domain.queries.QueryCursor
import com.rsba.component_microservice.domain.model.Item
import com.rsba.component_microservice.domain.usecase.common.RetrieveUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import java.util.*

@Component(value = "retrieve_item")
@OptIn(ExperimentalSerializationApi::class)
class RetrieveUseCaseImpl : RetrieveUseCase<Item> {
    override suspend fun invoke(database: DatabaseClient, first: Int, after: UUID?, token: UUID): List<Item> =
        database.sql(ItemQueries.retrieve(token = token, first = first, after = after))
            .map { row -> QueryCursor.all(row = row) }
            .first()
            .map { it?.mapNotNull { element -> (element as? ItemDao?)?.to } ?: emptyList() }
            .onErrorResume {
                throw it
            }
            .awaitFirstOrElse { emptyList() }
}