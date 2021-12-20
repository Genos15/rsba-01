package com.rsba.parameters_microservice.configuration.request_helper

import graphql.PublicApi
import graphql.relay.Connection
import graphql.relay.Edge
import graphql.relay.PageInfo

@PublicApi
data class CustomConnection<T>(
    private val edges: List<Edge<T>>,
    private val pageInfo: PageInfo,
) : Connection<T> {
    override fun getEdges(): List<Edge<T>> = edges
    override fun getPageInfo(): PageInfo = pageInfo
}