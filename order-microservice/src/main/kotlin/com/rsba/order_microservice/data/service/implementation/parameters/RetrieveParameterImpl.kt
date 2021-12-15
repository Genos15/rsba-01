package com.rsba.order_microservice.data.service.implementation.parameters

import com.rsba.order_microservice.database.ParameterDBHandler
import com.rsba.order_microservice.database.ParameterQueries
import kotlinx.coroutines.reactive.awaitFirstOrElse
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*
import com.rsba.order_microservice.domain.model.Parameter


interface RetrieveParameterImpl {

    suspend fun retrieveParametersFn(
        database: DatabaseClient,
        input: String? = null,
        first: Int,
        after: UUID? = null,
        token: UUID
    ): List<Parameter> = database.sql(
        input?.let {
            ParameterQueries.search(input = input, token = token, first = first, after = after)
        } ?: ParameterQueries.retrieve(token = token, first = first, after = after)
    ).map { row -> ParameterDBHandler.all(row = row) }
        .first()
        .onErrorResume {
            println("retrieveParametersFn=${it.message}")
            throw it
        }
        .log()
        .awaitFirstOrElse { emptyList() }

    suspend fun retrieveParametersByTaskId(
        database: DatabaseClient,
        taskId: UUID,
        token: UUID
    ): List<Parameter> = database.sql(ParameterQueries.retrieveByTaskId(taskId = taskId, token = token))
        .map { row -> ParameterDBHandler.all(row = row) }
        .first()
        .onErrorResume {
            println("retrieveParametersByTaskId=${it.message}")
            throw it
        }
        .log()
        .awaitFirstOrElse { emptyList() }

    suspend fun retrieveParametersByItemId(
        database: DatabaseClient,
        itemId: UUID,
        token: UUID
    ): List<Parameter> = database.sql(ParameterQueries.retrieveByItemId(itemId = itemId, token = token))
        .map { row -> ParameterDBHandler.all(row = row) }
        .first()
        .onErrorResume {
            println("retrieveParametersByItemId=${it.message}")
            throw it
        }
        .log()
        .awaitFirstOrElse { emptyList() }
}