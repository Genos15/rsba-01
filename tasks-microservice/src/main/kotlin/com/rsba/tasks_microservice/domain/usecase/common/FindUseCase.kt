package com.rsba.tasks_microservice.domain.usecase.common

import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface FindUseCase<T> {
    suspend operator fun invoke(database: DatabaseClient, id: UUID, token: UUID): Optional<T>
    suspend operator fun invoke(id: UUID, token: UUID): Optional<T>
}