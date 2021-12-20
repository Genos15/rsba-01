package com.rsba.parameters_microservice.data.service.usecase.parameters

import com.rsba.parameters_microservice.data.dao.ParameterDao
import com.rsba.parameters_microservice.domain.exception.CustomGraphQLError
import com.rsba.parameters_microservice.domain.queries.QueryCursor
import com.rsba.parameters_microservice.domain.model.Parameter
import com.rsba.parameters_microservice.domain.queries.IQueryGuesser
import com.rsba.parameters_microservice.domain.queries.query
import com.rsba.parameters_microservice.domain.usecase.common.FindUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.*

@Component(value = "find_parameter")
@OptIn(ExperimentalSerializationApi::class)
class FindUseCaseImpl : FindUseCase<Parameter>, IQueryGuesser {
    override suspend fun invoke(database: DatabaseClient, id: UUID, token: UUID): Optional<Parameter> =
        database.sql(query<ParameterDao>().find(id = id, token = token))
            .map { row -> QueryCursor.one(row = row) }
            .first()
            .map { Optional.ofNullable((it as? ParameterDao?)?.to) }
            .onErrorResume {
                if (it is CustomGraphQLError) {
                    Mono.empty()
                } else {
                    throw it
                }
            }
            .log()
            .awaitFirstOrElse { Optional.empty() }
}