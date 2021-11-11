package com.rsba.order_microservice.domain.usecase.custom.order

import com.rsba.order_microservice.domain.model.OrderCompletionLine
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface RetrieveOrderCompletionLineGraphUseCase {
    suspend operator fun invoke(database: DatabaseClient, year: Int, token: UUID): Optional<OrderCompletionLine>
}