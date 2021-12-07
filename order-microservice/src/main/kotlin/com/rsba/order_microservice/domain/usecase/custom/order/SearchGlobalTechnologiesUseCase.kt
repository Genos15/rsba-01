package com.rsba.order_microservice.domain.usecase.custom.order

import com.rsba.order_microservice.domain.model.OrderSearchInputValue
import com.rsba.order_microservice.domain.model.Technology
import graphql.relay.Connection
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface SearchGlobalTechnologiesUseCase {
    suspend operator fun invoke(
        database: DatabaseClient,
        ids: Set<OrderSearchInputValue>,
        token: UUID
    ): Map<OrderSearchInputValue, Connection<Technology>>
}