package com.rsba.component_microservice.domain.usecase.common

import com.rsba.component_microservice.domain.model.ElkGraph
import com.rsba.component_microservice.domain.model.ElkGraphItemCategoryNode
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface RetrieveFullElkGraphUseCase {
    suspend operator fun invoke(
        database: DatabaseClient,
        token: UUID,
        from: UUID? = null,
        height: Int,
        width: Int,
    ): ElkGraph<ElkGraphItemCategoryNode>
}