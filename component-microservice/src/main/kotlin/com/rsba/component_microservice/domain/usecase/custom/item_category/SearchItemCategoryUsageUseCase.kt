package com.rsba.component_microservice.domain.usecase.custom.item_category

import com.rsba.component_microservice.domain.model.InformationUsage
import org.springframework.r2dbc.core.DatabaseClient
import java.time.OffsetDateTime
import java.util.*

interface SearchItemCategoryUsageUseCase {
    suspend operator fun invoke(
        database: DatabaseClient,
        input: String,
        first: Int,
        after: UUID?,
        from: OffsetDateTime? = null,
        to: OffsetDateTime? = null,
        token: UUID
    ): List<InformationUsage>
}