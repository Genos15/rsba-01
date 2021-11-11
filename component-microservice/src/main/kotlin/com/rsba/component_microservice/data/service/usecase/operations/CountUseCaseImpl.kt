package com.rsba.component_microservice.data.service.usecase.operations

import com.rsba.component_microservice.data.service.usecase.queries.OperationQueries
import com.rsba.component_microservice.domain.format.QueryCursor
import com.rsba.component_microservice.domain.usecase.common.CountUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import java.util.*

@OptIn(ExperimentalSerializationApi::class)
@Component(value = "count_operation")
class CountUseCaseImpl : CountUseCase {
    override suspend fun invoke(database: DatabaseClient, token: UUID): Int =
        database.sql(OperationQueries.count(token = token))
            .map { row -> QueryCursor.count(row = row) }
            .first()
            .onErrorResume { throw it }
            .log()
            .awaitFirstOrElse { 0 }
}