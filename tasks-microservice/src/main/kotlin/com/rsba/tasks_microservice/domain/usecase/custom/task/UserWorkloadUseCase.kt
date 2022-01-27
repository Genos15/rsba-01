package com.rsba.tasks_microservice.domain.usecase.custom.task

import java.time.OffsetDateTime
import java.util.*

interface UserWorkloadUseCase {
    suspend operator fun invoke(
        id: UUID,
        rangeStart: OffsetDateTime,
        rangeEnd: OffsetDateTime,
        token: UUID
    ): Float
}