package com.rsba.tasks_microservice.data.service.usecase.task_set

import com.rsba.tasks_microservice.data.dao.TaskSetDao
import com.rsba.tasks_microservice.domain.queries.QueryCursor
import com.rsba.tasks_microservice.domain.input.TaskSetInput
import com.rsba.tasks_microservice.domain.model.MutationAction
import com.rsba.tasks_microservice.domain.model.TaskSet
import com.rsba.tasks_microservice.domain.queries.IQueryGuesser
import com.rsba.tasks_microservice.domain.queries.query
import com.rsba.tasks_microservice.domain.usecase.common.custom.task_set.CreateOrEditTaskSetUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import java.util.*

@Component(value = "create_edit_tasks_set")
@OptIn(ExperimentalSerializationApi::class)
class CreateOrEditUseCaseImpl(private val database: DatabaseClient) : CreateOrEditTaskSetUseCase, IQueryGuesser {
    override suspend fun invoke(input: TaskSetInput, action: MutationAction?, token: UUID): Optional<TaskSet> =
        database.sql(query<TaskSetDao>().createOrEdit(input = input, token = token, action = action))
            .map { row -> QueryCursor.one(row = row) }
            .first()
            .map { Optional.ofNullable((it.orElseGet { null } as? TaskSetDao?)?.to) }
            .onErrorResume { throw it }
            .log()
            .awaitFirstOrElse { Optional.empty() }

}