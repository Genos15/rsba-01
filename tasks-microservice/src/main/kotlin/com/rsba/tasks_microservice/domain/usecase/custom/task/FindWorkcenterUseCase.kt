package com.rsba.tasks_microservice.domain.usecase.custom.task

import com.rsba.tasks_microservice.domain.model.Workcenter
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface FindWorkcenterUseCase {
    suspend operator fun invoke(
        database: DatabaseClient,
        ids: Set<UUID>,
        token: UUID
    ): Map<UUID, Optional<Workcenter>>
}