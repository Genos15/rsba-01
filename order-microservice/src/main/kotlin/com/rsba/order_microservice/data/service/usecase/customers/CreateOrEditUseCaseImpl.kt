package com.rsba.order_microservice.data.service.usecase.customers

import com.rsba.order_microservice.data.dao.CustomerDao
import com.rsba.order_microservice.domain.input.CustomerInput
import com.rsba.order_microservice.domain.model.Customer
import com.rsba.order_microservice.domain.model.Edition
import com.rsba.order_microservice.domain.model.MutationAction
import com.rsba.order_microservice.domain.queries.IQueryGuesser
import com.rsba.order_microservice.domain.queries.QueryCursor
import com.rsba.order_microservice.domain.queries.query
import com.rsba.order_microservice.domain.usecase.common.CreateOrEditUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import java.util.*

@Component(value = "create_edit_customer")
@OptIn(ExperimentalSerializationApi::class)
class CreateOrEditUseCaseImpl : CreateOrEditUseCase<CustomerInput, Customer>, IQueryGuesser {
    override suspend fun invoke(
        database: DatabaseClient,
        input: CustomerInput,
        token: UUID,
        action: MutationAction?,
        case: Edition?,
    ): Optional<Customer> =
        database.sql(query<CustomerDao>().createOrEdit(input = input, token = token, action = action, case = case))
            .map { row -> QueryCursor.one(row = row) }
            .first()
            .map { Optional.ofNullable((it.orElseGet { null } as? CustomerDao?)?.to) }
            .onErrorResume { throw it }
            .log()
            .awaitFirstOrElse { Optional.empty() }
}