package com.rsba.tasks_microservice.domain.usecase.common

import com.rsba.tasks_microservice.domain.model.Task
import java.util.*

interface RetrieveTasksUseCase {
    suspend operator fun invoke(
        ids: Set<UUID>,
        first: Int,
        after: UUID? = null,
        token: UUID
    ): Map<UUID, List<Task>>
}