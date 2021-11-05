package com.rsba.component_microservice.domain.usecase.common

import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface RetrieveUseCase<T> {
    suspend operator fun invoke(database: DatabaseClient, first: Int, after: UUID?, token: UUID): List<T>
}