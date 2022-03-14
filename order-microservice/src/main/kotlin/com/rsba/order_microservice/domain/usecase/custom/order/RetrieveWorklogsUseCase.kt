package com.rsba.order_microservice.domain.usecase.custom.order

import com.rsba.order_microservice.domain.model.Worklog
import java.time.OffsetDateTime
import java.util.*

interface RetrieveWorklogsUseCase {
    suspend operator fun invoke(
        ids: Set<UUID>,
        first: Int,
        after: UUID? = null,
        rangeStart: OffsetDateTime? = null,
        rangeEnd: OffsetDateTime? = null,
        token: UUID
    ): Map<UUID, List<Worklog>>
}