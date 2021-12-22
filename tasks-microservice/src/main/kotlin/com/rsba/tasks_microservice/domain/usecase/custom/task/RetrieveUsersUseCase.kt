package com.rsba.tasks_microservice.domain.usecase.custom.task

import com.rsba.tasks_microservice.domain.model.User
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface RetrieveUsersUseCase {
    suspend operator fun invoke(
        database: DatabaseClient,
        ids: Set<UUID>,
        first: Int,
        after: UUID? = null,
        token: UUID
    ): Map<UUID, List<User>>
}