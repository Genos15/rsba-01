package com.rsba.tasks_microservice.domain.usecase.custom.task

import com.rsba.tasks_microservice.domain.model.Technology
import java.util.*

interface RetrieveTechnologiesUseCase {
    suspend operator fun invoke(
        ids: Set<UUID>,
        first: Int,
        after: UUID? = null,
        token: UUID
    ): Map<UUID, List<Technology>>
}