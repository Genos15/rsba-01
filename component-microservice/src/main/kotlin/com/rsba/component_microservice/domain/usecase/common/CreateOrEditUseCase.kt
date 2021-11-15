package com.rsba.component_microservice.domain.usecase.common

import com.rsba.component_microservice.domain.model.Edition
import com.rsba.component_microservice.domain.model.EditionCase
import com.rsba.component_microservice.domain.model.MutationAction
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface CreateOrEditUseCase<I, T> {
    suspend operator fun invoke(
        database: DatabaseClient,
        input: I,
        token: UUID,
        action: MutationAction? = null,
        case: Edition? = null,
    ): Optional<T>
}