package com.rsba.tasks_microservice.domain.usecase.common.custom.task_set

import com.rsba.tasks_microservice.domain.input.TaskSetInput
import com.rsba.tasks_microservice.domain.model.MutationAction
import com.rsba.tasks_microservice.domain.model.TaskSet
import java.util.*

interface CreateOrEditTaskSetUseCase {
    suspend operator fun invoke(
        input: TaskSetInput,
        action: MutationAction? = null,
        token: UUID,
    ): Optional<TaskSet>
}