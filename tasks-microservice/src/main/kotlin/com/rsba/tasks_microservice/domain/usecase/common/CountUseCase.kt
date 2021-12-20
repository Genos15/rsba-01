package com.rsba.parameters_microservice.domain.usecase.common

import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface CountUseCase {
    suspend operator fun invoke(database: DatabaseClient, token: UUID): Int
}