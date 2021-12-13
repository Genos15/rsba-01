package com.rsba.order_microservice.data.service.implementation.graphs.elk_graph

interface ConstructElkImpl {

//    suspend fun constructElkFn(database: DatabaseClient, input: ElkGraphInput, token: UUID): ElkGraphData = mono {
//        val items = database.sql(GraphItemDBQueries.retrieveElkItems(input = input, token = token))
//            .map { row -> GraphItemDBHandler.all(row = row) }
//            .first()
//            .awaitFirstOrDefault(emptyList())
//        ElkGraphData(entries = items, height = input.height, width = input.width)
//    }.log().awaitFirst()
}