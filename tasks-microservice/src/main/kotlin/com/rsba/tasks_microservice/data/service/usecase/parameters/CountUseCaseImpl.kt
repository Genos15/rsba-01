package com.rsba.parameters_microservice.data.service.usecase.parameters

import com.rsba.parameters_microservice.data.dao.ParameterDao
import com.rsba.parameters_microservice.domain.queries.IQueryGuesser
import com.rsba.parameters_microservice.domain.queries.QueryCursor
import com.rsba.parameters_microservice.domain.queries.query
import com.rsba.parameters_microservice.domain.usecase.common.CountUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import java.util.*

@OptIn(ExperimentalSerializationApi::class)
@Component(value = "count_parameters")
class CountUseCaseImpl : CountUseCase, IQueryGuesser {
    override suspend fun invoke(database: DatabaseClient, token: UUID): Int =
        database.sql(query<ParameterDao>().count(token = token))
            .map { row -> QueryCursor.count(row = row) }
            .first()
            .onErrorResume { throw it }
            .log()
            .awaitFirstOrElse { 0 }
}