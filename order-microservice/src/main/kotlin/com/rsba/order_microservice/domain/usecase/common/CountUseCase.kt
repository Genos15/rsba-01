package com.rsba.order_microservice.domain.usecase.common

import com.rsba.order_microservice.domain.model.AbstractStatus
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface CountUseCase {
    suspend operator fun invoke(database: DatabaseClient, status: AbstractStatus? = null, token: UUID): Int
}