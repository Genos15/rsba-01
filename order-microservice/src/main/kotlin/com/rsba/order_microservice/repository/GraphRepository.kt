package com.rsba.order_microservice.repository

import com.rsba.order_microservice.domain.input.ElkGraphInput
import com.rsba.order_microservice.domain.model.ElkGraphData
import java.util.*

interface GraphRepository {
    suspend fun constructElkGraph(input: ElkGraphInput, token: UUID): ElkGraphData
}