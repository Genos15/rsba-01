package com.rsba.order_microservice.service

import com.rsba.order_microservice.domain.input.ElkGraphInput
import com.rsba.order_microservice.domain.model.ElkGraphData
import com.rsba.order_microservice.repository.GraphRepository
import mu.KLogger
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import java.util.*

@Service
class GraphService(private val database: DatabaseClient, private val logger: KLogger) : GraphRepository {

    override suspend fun constructElkGraph(input: ElkGraphInput, token: UUID): ElkGraphData {
        TODO("Not yet implemented")
    }

}