package com.rsba.usermicroservice.service.implementation.executors


import com.rsba.usermicroservice.context.token.ITokenImpl
import com.rsba.usermicroservice.domain.model.Executor
import com.rsba.usermicroservice.query.database.ExecutorDBHandler
import com.rsba.usermicroservice.query.database.ExecutorQueries
import kotlinx.coroutines.reactive.awaitFirstOrElse
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface RetrieveExecutorImpl : ITokenImpl {

    suspend fun retrieveImpl(
        database: DatabaseClient,
        input: String? = null,
        first: Int,
        after: UUID? = null,
        token: UUID
    ): List<Executor> = database.sql(
        input?.let {
            ExecutorQueries.search(input = input, token = token, first = first, after = after)
        } ?: ExecutorQueries.retrieve(token = token, first = first, after = after)
    ).map { row -> ExecutorDBHandler.all(row = row) }
        .first()
        .log()
        .onErrorResume {
            println("RetrieveExecutorImpl->retrieve->error=${it.message}")
            throw it
        }
        .awaitFirstOrElse { emptyList() }

    suspend fun retrieveByIdImpl(
        database: DatabaseClient,
        id: UUID,
        token: UUID
    ): Optional<Executor> = database.sql(ExecutorQueries.retrieveById(id = id, token = token))
        .map { row -> ExecutorDBHandler.one(row = row) }
        .first()
        .log()
        .onErrorResume {
            println("RetrieveExecutorImpl->retrieveById->error=${it.message}")
            throw it
        }
        .awaitFirstOrElse { Optional.empty() }
}