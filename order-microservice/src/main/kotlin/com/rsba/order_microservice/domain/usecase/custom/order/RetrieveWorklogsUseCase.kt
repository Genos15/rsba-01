package com.rsba.order_microservice.domain.usecase.custom.order

import com.rsba.order_microservice.domain.model.Worklog
import java.util.*

interface RetrieveWorklogsUseCase {
    suspend operator fun invoke(
        ids: Set<UUID>,
        first: Int,
        after: UUID? = null,
        token: UUID
    ): Map<UUID, List<Worklog>>
}