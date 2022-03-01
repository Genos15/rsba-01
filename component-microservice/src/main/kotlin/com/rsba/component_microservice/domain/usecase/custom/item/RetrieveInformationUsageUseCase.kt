package com.rsba.component_microservice.domain.usecase.custom.item

import com.rsba.component_microservice.domain.model.InformationUsage
import java.time.OffsetDateTime
import java.util.*

interface RetrieveInformationUsageUseCase {
    suspend operator fun invoke(
        first: Int,
        after: UUID?,
        from: OffsetDateTime? = null,
        to: OffsetDateTime? = null,
        token: UUID
    ): List<InformationUsage>
}