package com.rsba.component_microservice.resolver.query

import com.rsba.component_microservice.domain.format.ICursorUtil
import graphql.relay.*
import java.util.*

interface GenericRetrieveConnection : ICursorUtil {
    fun <T> perform(entry: List<T>, first: Int, after: UUID? = null): Connection<T> {
        val edges: List<Edge<T>> = entry.filterNotNull().map { source ->
            val fieldId = source::class.java.fields.singleOrNull { f ->
                f.name.contentEquals("id", ignoreCase = true)
            }
            return@map DefaultEdge(source, createCursorWith(id = (fieldId?.get(source) as? UUID?) ?: UUID.randomUUID()))
        }.take(first)

        val pageInfo = DefaultPageInfo(
            firstCursorFrom(edges),
            lastCursorFrom(edges),
            after != null,
            edges.size >= first
        )
        return DefaultConnection(edges, pageInfo)
    }
}