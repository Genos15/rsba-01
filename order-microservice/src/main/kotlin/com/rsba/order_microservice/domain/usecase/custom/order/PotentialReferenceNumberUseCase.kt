package com.rsba.order_microservice.domain.usecase.custom.order

import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface PotentialReferenceNumberUseCase {
    suspend operator fun invoke(database: DatabaseClient, companyId: UUID = UUID.randomUUID(), token: UUID): String
}