package com.rsba.component_microservice.data.service.usecase.item_category

import com.rsba.component_microservice.data.dao.ItemCategoryDao
import com.rsba.component_microservice.data.service.usecase.queries.ItemCategoryQueries
import com.rsba.component_microservice.domain.exception.CustomGraphQLError
import com.rsba.component_microservice.domain.queries.QueryCursor
import com.rsba.component_microservice.domain.model.ItemCategory
import com.rsba.component_microservice.domain.usecase.common.FindUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.*

@Component(value = "find_item_category")
@OptIn(ExperimentalSerializationApi::class)
class FindUseCaseImpl : FindUseCase<ItemCategory> {
    override suspend fun invoke(database: DatabaseClient, id: UUID, token: UUID): Optional<ItemCategory> =
        database.sql(ItemCategoryQueries.find(id = id, token = token))
            .map { row -> QueryCursor.one(row = row) }
            .first()
            .map { Optional.ofNullable((it as? ItemCategoryDao?)?.to) }
            .onErrorResume {
                if (it is CustomGraphQLError) {
                    Mono.empty()
                } else {
                    println("retrieveByIdImpl=${it.message}")
                    throw it
                }
            }
            .log()
            .awaitFirstOrElse { Optional.empty() }
}