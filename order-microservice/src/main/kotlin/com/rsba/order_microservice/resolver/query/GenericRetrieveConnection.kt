package com.rsba.order_microservice.resolver.query

import com.rsba.order_microservice.configuration.request_helper.ICursorUtil
import graphql.relay.*
import mu.KLogger
import java.util.*

abstract class GenericRetrieveConnection(val myLogger: KLogger) : ICursorUtil {

    fun <T> retrieveFn(entry: List<T>, first: Int, after: UUID? = null): Connection<T> {
        val edges: List<Edge<T>> = entry.filterNotNull().map { source ->
            val fieldId = source::class.java.fields.singleOrNull { f ->
                f.name.contentEquals("id", ignoreCase = true)
            }
            myLogger.warn { "${source::class.java.fields} by $fieldId" }
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