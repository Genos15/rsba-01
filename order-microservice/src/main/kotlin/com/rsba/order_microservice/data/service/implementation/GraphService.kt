package com.rsba.order_microservice.data.service.implementation

import com.rsba.order_microservice.domain.repository.GraphRepository
import com.rsba.order_microservice.data.service.implementation.graphs.elk_graph.ConstructElkImpl
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service

@Service
class GraphService(private val database: DatabaseClient) : GraphRepository,
    ConstructElkImpl {
//    override suspend fun constructElkGraph(input: ElkGraphInput, token: UUID): ElkGraphData =
//        constructElkFn(database = database, input = input, token = token)
}