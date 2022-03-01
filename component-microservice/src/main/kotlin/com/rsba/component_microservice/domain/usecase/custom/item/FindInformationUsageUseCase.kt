package com.rsba.component_microservice.domain.usecase.custom.item

import com.rsba.component_microservice.domain.model.InformationUsage
import java.time.OffsetDateTime
import java.util.*

interface FindInformationUsageUseCase {
    suspend operator fun invoke(
        input: UUID,
        from: OffsetDateTime? = null,
        to: OffsetDateTime? = null,
        token: UUID
    ): Optional<InformationUsage>
}