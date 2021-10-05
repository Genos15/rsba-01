package com.rsba.component_microservice.service.implementations.items

import com.rsba.component_microservice.domain.model.Item
import com.rsba.component_microservice.query.database.ItemDBHandler2
import com.rsba.component_microservice.query.database.ItemQueries
import kotlinx.coroutines.reactive.awaitFirstOrElse
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface RetrieveItemsImpl {

    suspend fun searchItemsImplFn(
        database: DatabaseClient,
        input: String,
        first: Int,
        after: UUID? = null,
        token: UUID
    ): List<Item> = database.sql(ItemQueries.search(input = input, token = token, first = first, after = after))
        .map { row -> ItemDBHandler2.all(row = row) }
        .first()
        .onErrorResume {
            println("searchItemsImplFn=${it.message}")
            throw it
        }
        .awaitFirstOrElse { emptyList() }

}