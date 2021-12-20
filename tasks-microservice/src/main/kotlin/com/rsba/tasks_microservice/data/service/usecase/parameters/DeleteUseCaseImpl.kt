package com.rsba.parameters_microservice.data.service.usecase.parameters

import com.rsba.parameters_microservice.data.dao.ParameterDao
import com.rsba.parameters_microservice.domain.queries.QueryCursor
import com.rsba.parameters_microservice.domain.model.Parameter
import com.rsba.parameters_microservice.domain.queries.IQueryGuesser
import com.rsba.parameters_microservice.domain.queries.query
import com.rsba.parameters_microservice.domain.usecase.common.DeleteUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import java.util.*

@Component(value = "delete_parameter")
@OptIn(ExperimentalSerializationApi::class)
class DeleteUseCaseImpl : DeleteUseCase<Parameter>, IQueryGuesser {
    override suspend fun invoke(database: DatabaseClient, input: UUID, token: UUID): Boolean =
        database.sql(query<ParameterDao>().delete(input = input, token = token))
            .map { row -> QueryCursor.isTrue(row = row) }
            .first()
            .onErrorResume { throw it }
            .log()
            .awaitFirstOrElse { false }
}