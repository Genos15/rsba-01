package com.rsba.order_microservice.domain.usecase.common

import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface CreateOrEditUseCase<I, T> {
    suspend operator fun invoke(database: DatabaseClient, input: I, token: UUID): Optional<T>
}