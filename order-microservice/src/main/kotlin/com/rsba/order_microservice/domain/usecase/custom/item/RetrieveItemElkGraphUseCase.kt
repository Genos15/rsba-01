package com.rsba.order_microservice.domain.usecase.custom.item


import com.rsba.order_microservice.domain.model.ElkGraph
import com.rsba.order_microservice.domain.model.ElkGraphItemNode
import org.springframework.r2dbc.core.DatabaseClient
import java.util.*

interface RetrieveItemElkGraphUseCase {
    suspend operator fun invoke(
        database: DatabaseClient,
        token: UUID,
        from: UUID? = null,
        orderId: UUID? = null,
        height: Int,
        width: Int,
    ): ElkGraph<ElkGraphItemNode>
}