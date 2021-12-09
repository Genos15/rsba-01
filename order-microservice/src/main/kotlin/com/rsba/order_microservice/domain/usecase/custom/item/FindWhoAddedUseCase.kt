package com.rsba.order_microservice.domain.usecase.custom.item

import com.rsba.order_microservice.domain.model.User
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface FindWhoAddedUseCase {
    suspend operator fun invoke(
        database: DatabaseClient,
        ids: Set<UUID>,
        token: UUID
    ): Map<UUID, Optional<User>>
}