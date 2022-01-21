package com.rsba.tasks_microservice.domain.usecase.custom.task

import com.rsba.tasks_microservice.domain.model.Task
import java.util.*

interface ToExecuteTaskUseCase {
    suspend operator fun invoke(
        id: UUID,
        quantity: Int? = null,
        token: UUID
    ): Optional<Task>
}