package com.rsba.order_microservice.data.service.usecase.orders

import com.rsba.order_microservice.data.dao.TaskDao
import com.rsba.order_microservice.data.service.usecase.queries.OrderQueries
import com.rsba.order_microservice.domain.input.TaskInput
import com.rsba.order_microservice.domain.model.MutationAction
import com.rsba.order_microservice.domain.model.Task
import com.rsba.order_microservice.domain.queries.QueryCursor
import com.rsba.order_microservice.domain.usecase.custom.order.EditTaskUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import java.util.*

@Component
@OptIn(ExperimentalSerializationApi::class)
class EditTaskUseCaseImpl : EditTaskUseCase {
    override suspend fun invoke(
        database: DatabaseClient,
        input: TaskInput,
        token: UUID,
        action: MutationAction?
    ): Optional<Task> =
        database.sql(OrderQueries.editTask(input = input, token = token, action = action))
            .map { row -> QueryCursor.one(row = row) }
            .first()
            .map { Optional.ofNullable((it.orElseGet { null } as? TaskDao?)?.to) }
            .onErrorResume { throw it }
            .log()
            .awaitFirstOrElse { Optional.empty() }
}