package com.rsba.order_microservice.domain.usecase.custom.customer

import com.rsba.order_microservice.domain.model.Customer
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface RetrieveCustomerEntitiesUseCase {
    suspend operator fun invoke(
        database: DatabaseClient,
        ids: Set<UUID>,
        first: Int,
        after: UUID?,
        token: UUID
    ): Map<UUID, List<Customer>>
}