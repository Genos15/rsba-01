package com.rsba.usermicroservice.service.implementation.executors

import com.rsba.usermicroservice.context.token.ITokenImpl
import com.rsba.usermicroservice.query.database.ExecutorDBHandler
import com.rsba.usermicroservice.query.database.ExecutorQueries
import kotlinx.coroutines.reactive.awaitFirstOrElse
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface DeleteExecutorImpl : ITokenImpl {

    suspend fun deleteImpl(
        database: DatabaseClient,
        input: UUID,
        token: UUID
    ): Boolean = database.sql(ExecutorQueries.delete(input = input, token = token))
        .map { row -> ExecutorDBHandler.count(row = row) }.first()
        .map { it != null && it > 0 }
        .onErrorResume {
            println("DeleteExecutorImpl->deleteImpl->error=${it.message}")
            throw it
        }
        .awaitFirstOrElse { false }

}