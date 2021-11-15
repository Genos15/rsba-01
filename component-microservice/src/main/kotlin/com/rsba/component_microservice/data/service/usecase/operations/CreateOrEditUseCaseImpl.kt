package com.rsba.component_microservice.data.service.usecase.operations

import com.rsba.component_microservice.data.dao.OperationDao
import com.rsba.component_microservice.data.service.usecase.queries.OperationQueries
import com.rsba.component_microservice.domain.queries.QueryCursor
import com.rsba.component_microservice.domain.input.OperationInput
import com.rsba.component_microservice.domain.model.*
import com.rsba.component_microservice.domain.usecase.common.CreateOrEditUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import java.util.*

@Component(value = "create_edit_operation")
@OptIn(ExperimentalSerializationApi::class)
class CreateOrEditUseCaseImpl : CreateOrEditUseCase<OperationInput, Operation> {
    override suspend fun invoke(
        database: DatabaseClient,
        input: OperationInput,
        token: UUID,
        action: MutationAction?,
        case: Edition?,
    ): Optional<Operation> =
        database.sql(OperationQueries.createOrEdit(input = input, token = token, action = action, case = case))
            .map { row -> QueryCursor.one(row = row) }
            .first()
            .map { Optional.ofNullable((it as? OperationDao?)?.to) }
            .onErrorResume { throw it }
            .log()
            .awaitFirstOrElse { Optional.empty() }
}