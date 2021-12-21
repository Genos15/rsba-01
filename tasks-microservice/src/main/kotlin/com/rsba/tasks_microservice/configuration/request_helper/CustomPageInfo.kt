package com.rsba.tasks_microservice.configuration.request_helper

import com.rsba.tasks_microservice.domain.format.ICursorUtil
import graphql.PublicApi
import graphql.relay.ConnectionCursor
import graphql.relay.Edge
import graphql.relay.PageInfo
import java.util.*

@PublicApi
data class CustomPageInfo<T>(
    private val edges: List<Edge<T>>,
    private val first: Int,
    private val after: UUID? = null
) : PageInfo, ICursorUtil {

    override fun getStartCursor(): ConnectionCursor? = firstCursorFrom(edges)

    override fun getEndCursor(): ConnectionCursor? = lastCursorFrom(edges)

    override fun isHasPreviousPage(): Boolean = after != null

    override fun isHasNextPage(): Boolean = edges.size >= first
}