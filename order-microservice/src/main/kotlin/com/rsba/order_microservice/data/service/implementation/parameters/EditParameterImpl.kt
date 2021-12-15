package com.rsba.order_microservice.data.service.implementation.parameters

import com.rsba.order_microservice.database.ParameterDBHandler
import com.rsba.order_microservice.database.ParameterQueries
import com.rsba.order_microservice.domain.input.ParameterInput
import com.rsba.order_microservice.domain.model.Parameter
import kotlinx.coroutines.reactive.awaitFirstOrElse
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface EditParameterImpl {

    suspend fun createOrEditParameterFn(
        database: DatabaseClient,
        input: ParameterInput,
        token: UUID
    ): Optional<Parameter> = database.sql(ParameterQueries.createOrEdit(input = input, token = token))
        .map { row -> ParameterDBHandler.one(row = row) }.first()
        .onErrorResume {
            println("createOrEditParameterFn=${it.message}")
            throw it
        }
        .log()
        .awaitFirstOrElse { Optional.empty() }

    suspend fun addRemovePotentialValueToParameterFn(
        database: DatabaseClient,
        input: ParameterInput,
        token: UUID
    ): Optional<Parameter> = database.sql(ParameterQueries.addOrRemovePotentialValue(input = input, token = token))
        .map { row -> ParameterDBHandler.one(row = row) }.first()
        .onErrorResume {
            println("addRemovePotentialValueToParameterFn=${it.message}")
            throw it
        }
        .log()
        .awaitFirstOrElse { Optional.empty() }


    suspend fun deleteParameterFn(
        database: DatabaseClient,
        input: UUID,
        token: UUID
    ): Boolean = database.sql(ParameterQueries.delete(input = input, token = token))
        .map { row -> ParameterDBHandler.count(row = row) }.first()
        .map { it != null && it > 0 }
        .onErrorResume {
            println("deleteParameterFn=${it.message}")
            throw it
        }
        .log()
        .awaitFirstOrElse { false }
}