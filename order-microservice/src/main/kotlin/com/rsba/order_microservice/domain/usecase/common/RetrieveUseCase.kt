package com.rsba.order_microservice.domain.usecase.common

import com.rsba.order_microservice.domain.model.AbstractLayer
import com.rsba.order_microservice.domain.model.AbstractStatus
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface RetrieveUseCase<T> {
    suspend operator fun invoke(
        database: DatabaseClient,
        first: Int,
        after: UUID? = null,
        layer: AbstractLayer? = null,
        status: AbstractStatus? = null,
        token: UUID
    ): List<T>
}