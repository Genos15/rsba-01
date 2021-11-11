package com.rsba.component_microservice.data.service.usecase.operations

import com.rsba.component_microservice.data.dao.OperationDao
import com.rsba.component_microservice.data.service.usecase.queries.OperationQueries
import com.rsba.component_microservice.domain.exception.CustomGraphQLError
import com.rsba.component_microservice.domain.format.QueryCursor
import com.rsba.component_microservice.domain.model.Operation
import com.rsba.component_microservice.domain.usecase.common.FindUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.*

@Component(value = "find_operation")
@OptIn(ExperimentalSerializationApi::class)
class FindUseCaseImpl : FindUseCase<Operation> {
    override suspend fun invoke(database: DatabaseClient, id: UUID, token: UUID): Optional<Operation> =
        database.sql(OperationQueries.find(id = id, token = token))
            .map { row -> QueryCursor.one(row = row) }
            .first()
            .map { Optional.ofNullable((it as? OperationDao?)?.to) }
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