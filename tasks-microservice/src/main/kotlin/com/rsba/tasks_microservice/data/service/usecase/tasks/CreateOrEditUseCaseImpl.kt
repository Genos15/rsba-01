package com.rsba.tasks_microservice.data.service.usecase.tasks

import com.rsba.tasks_microservice.data.dao.TaskDao
import com.rsba.tasks_microservice.domain.queries.QueryCursor
import com.rsba.tasks_microservice.domain.input.TaskInput
import com.rsba.tasks_microservice.domain.model.Edition
import com.rsba.tasks_microservice.domain.model.MutationAction
import com.rsba.tasks_microservice.domain.model.Task
import com.rsba.tasks_microservice.domain.queries.IQueryGuesser
import com.rsba.tasks_microservice.domain.queries.query
import com.rsba.tasks_microservice.domain.usecase.common.CreateOrEditUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import java.util.*

@Component(value = "create_edit_task")
@OptIn(ExperimentalSerializationApi::class)
class CreateOrEditUseCaseImpl : CreateOrEditUseCase<TaskInput, Task>, IQueryGuesser {
    override suspend fun invoke(
        database: DatabaseClient,
        input: TaskInput,
        token: UUID,
        action: MutationAction?,
        case: Edition?,
    ): Optional<Task> =
        database.sql(query<TaskDao>().createOrEdit(input = input, token = token, action = action, case = case))
            .map { row -> QueryCursor.one(row = row) }
            .first()
            .map { Optional.ofNullable((it.orElseGet { null } as? TaskDao?)?.to) }
            .onErrorResume { throw it }
            .log()
            .awaitFirstOrElse { Optional.empty() }
}