package com.rsba.tasks_microservice.domain.usecase.common

import com.rsba.tasks_microservice.domain.model.User
import java.util.*

interface RetrieveUsersUseCase {
    suspend operator fun invoke(
        ids: Set<UUID>,
        first: Int,
        after: UUID? = null,
        token: UUID
    ): Map<UUID, List<User>>
}