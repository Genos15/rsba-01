package com.rsba.tasks_microservice.domain.usecase.common.custom.comment

import com.rsba.tasks_microservice.domain.model.User
import java.util.*

interface FindReporterUseCase {
    suspend operator fun invoke(
        ids: Set<UUID>,
        token: UUID
    ): Map<UUID, Optional<User>>
}