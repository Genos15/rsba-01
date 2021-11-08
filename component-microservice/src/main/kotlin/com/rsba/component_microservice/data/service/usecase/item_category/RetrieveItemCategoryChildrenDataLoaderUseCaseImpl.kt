package com.rsba.component_microservice.data.service.usecase.item_category

import com.rsba.component_microservice.data.dao.ItemCategoryDao
import com.rsba.component_microservice.data.service.usecase.queries.ItemCategoryQueries
import com.rsba.component_microservice.domain.format.QueryCursor
import com.rsba.component_microservice.domain.model.ItemCategory
import com.rsba.component_microservice.domain.usecase.custom.item_category.RetrieveItemCategoryChildrenDataLoaderUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.util.*


@Component
@OptIn(ExperimentalSerializationApi::class)
class RetrieveItemCategoryChildrenDataLoaderUseCaseImpl : RetrieveItemCategoryChildrenDataLoaderUseCase {
    override suspend fun invoke(database: DatabaseClient, ids: Set<UUID>, token: UUID): Map<UUID, List<ItemCategory>> =
        Flux.fromIterable(ids)
            .parallel()
            .flatMap { id ->
                database.sql(ItemCategoryQueries.retrieveChildren(token = token, id = id))
                    .map { row -> QueryCursor.all(row = row) }
                    .first()
                    .map { it?.mapNotNull { element -> (element as? ItemCategoryDao?)?.to } ?: emptyList() }
                    .map { AbstractMap.SimpleEntry(id, it) }
                    .onErrorResume { throw it }
            }
            .runOn(Schedulers.parallel())
            .sequential()
            .collectList()
            .map { entries -> entries.associateBy({ it.key }, { it.value ?: emptyList() }) }
            .onErrorResume { throw it }
            .log()
            .awaitFirstOrElse { emptyMap() }
}

