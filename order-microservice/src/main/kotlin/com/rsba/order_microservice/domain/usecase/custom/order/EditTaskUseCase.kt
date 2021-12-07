package com.rsba.order_microservice.domain.usecase.custom.order

import com.rsba.order_microservice.domain.input.TaskInput
import com.rsba.order_microservice.domain.model.MutationAction
import com.rsba.order_microservice.domain.model.Task
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface EditTaskUseCase {
    suspend operator fun invoke(
        database: DatabaseClient,
        input: TaskInput,
        token: UUID,
        action: MutationAction? = null
    ): Optional<Task>
}