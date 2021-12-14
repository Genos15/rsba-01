package com.rsba.parameters_microservice.data.service.usecase.parameters

import com.rsba.parameters_microservice.data.dao.ParameterDao
import com.rsba.parameters_microservice.domain.queries.QueryCursor
import com.rsba.parameters_microservice.domain.model.Parameter
import com.rsba.parameters_microservice.domain.queries.IQueryGuesser
import com.rsba.parameters_microservice.domain.queries.query
import com.rsba.parameters_microservice.domain.usecase.common.RetrieveUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import java.util.*

@Component(value = "retrieve_parameters")
@OptIn(ExperimentalSerializationApi::class)
class RetrieveUseCaseImpl : RetrieveUseCase<Parameter>, IQueryGuesser {
    override suspend fun invoke(database: DatabaseClient, first: Int, after: UUID?, token: UUID): List<Parameter> =
        database.sql(query<ParameterDao>().retrieve(token = token, first = first, after = after))
            .map { row -> QueryCursor.all(row = row) }
            .first()
            .map { it?.mapNotNull { element -> (element as? ParameterDao?)?.to } ?: emptyList() }
            .onErrorResume {
                throw it
            }
            .awaitFirstOrElse { emptyList() }
}