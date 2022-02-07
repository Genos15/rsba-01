package com.rsba.tasks_microservice.domain.usecase.custom.user

import com.rsba.tasks_microservice.domain.model.User
import com.rsba.tasks_microservice.domain.model.UserActivityLayer
import java.time.OffsetDateTime
import java.util.*

interface RetrieveUserActivityUseCase {
    suspend operator fun invoke(
        first: Int,
        after: UUID?,
        layer: UserActivityLayer,
        rangeStart: OffsetDateTime,
        rangeEnd: OffsetDateTime,
        token: UUID
    ): List<User>
}