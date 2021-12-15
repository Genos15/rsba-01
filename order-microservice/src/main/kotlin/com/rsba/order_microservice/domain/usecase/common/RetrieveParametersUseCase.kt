package com.rsba.order_microservice.domain.usecase.common

import com.rsba.order_microservice.domain.model.Parameter
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface RetrieveParametersUseCase {
    suspend operator fun invoke(
        database: DatabaseClient,
        ids: Set<UUID>,
        first: Int,
        after: UUID? = null,
        token: UUID
    ): Map<UUID, List<Parameter>>
}