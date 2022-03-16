package com.rsba.tasks_microservice.domain.usecase.common.custom.task_set

import com.rsba.tasks_microservice.domain.model.TaskSet
import java.util.*

interface ToExecuteTaskSetUseCase {
    suspend operator fun invoke(
        id: UUID,
        quantity: Int? = null,
        token: UUID
    ): Optional<TaskSet>
}