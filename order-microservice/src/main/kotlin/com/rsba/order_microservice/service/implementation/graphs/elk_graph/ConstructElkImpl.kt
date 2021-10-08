package com.rsba.order_microservice.service.implementation.graphs.elk_graph

import com.rsba.order_microservice.database.GraphItemDBHandler
import com.rsba.order_microservice.database.GraphItemDBQueries
import com.rsba.order_microservice.domain.input.ElkGraphInput
import com.rsba.order_microservice.domain.model.ElkGraphData
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrDefault
import kotlinx.coroutines.reactor.mono
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface ConstructElkImpl {

    suspend fun constructElkFn(database: DatabaseClient, input: ElkGraphInput, token: UUID): ElkGraphData = mono {
        val items = database.sql(GraphItemDBQueries.retrieveElkItems(input = input, token = token))
            .map { row -> GraphItemDBHandler.all(row = row) }
            .first()
            .awaitFirstOrDefault(emptyList())
        ElkGraphData(entries = items, height = input.height, width = input.width)
    }.awaitFirst()
}