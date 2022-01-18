package com.rsba.tasks_microservice.domain.usecase.common

import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface DeleteUseCase<T> {
    suspend operator fun invoke(database: DatabaseClient, input: UUID, token: UUID): Boolean
    suspend operator fun invoke(input: UUID, token: UUID): Boolean
}