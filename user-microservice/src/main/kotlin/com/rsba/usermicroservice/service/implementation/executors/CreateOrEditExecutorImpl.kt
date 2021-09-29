package com.rsba.usermicroservice.service.implementation.executors


import com.rsba.usermicroservice.context.token.ITokenImpl
import com.rsba.usermicroservice.domain.input.ExecutorInput
import com.rsba.usermicroservice.domain.model.Executor
import com.rsba.usermicroservice.exception.CustomGraphQLError
import com.rsba.usermicroservice.query.database.ExecutorDBHandler
import com.rsba.usermicroservice.query.database.ExecutorQueries
import kotlinx.coroutines.reactive.awaitFirstOrElse
import org.apache.commons.validator.routines.EmailValidator
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface CreateOrEditExecutorImpl : ITokenImpl {

    suspend fun createOrEditImpl(
        database: DatabaseClient,
        input: ExecutorInput,
        token: UUID
    ): Optional<Executor> = run {
        if (!EmailValidator.getInstance().isValid(input.email)) {
            throw CustomGraphQLError("Адрес электронной почты недействителен")
        }

        database.sql(ExecutorQueries.createOrEdit(input = input, token = token))
            .map { row -> ExecutorDBHandler.one(row = row) }.first()
            .onErrorResume {
                println("CreateOrEditExecutorImpl->createOrEdit->error=${it.message}")
                throw it
            }
            .awaitFirstOrElse { Optional.empty() }
    }


    suspend fun renewPinImpl(
        database: DatabaseClient,
        input: ExecutorInput,
        token: UUID
    ): Optional<Executor> = database.sql(ExecutorQueries.renewPin(input = input, token = token))
        .map { row -> ExecutorDBHandler.one(row = row) }.first()
        .onErrorResume {
            println("CreateOrEditExecutorImpl->renewPinImpl->error=${it.message}")
            throw it
        }
        .awaitFirstOrElse { Optional.empty() }

}