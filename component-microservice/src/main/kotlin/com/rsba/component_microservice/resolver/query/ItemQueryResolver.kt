package com.rsba.component_microservice.resolver.query

import com.rsba.component_microservice.configuration.request_helper.CursorUtil
import com.rsba.component_microservice.domain.model.Item
import com.rsba.component_microservice.aspect.AdminSecured
import com.rsba.component_microservice.domain.repository.ItemRepository
import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.relay.*
import org.springframework.stereotype.Component
import java.util.*

import graphql.schema.DataFetchingEnvironment
import mu.KLogger


@Component
class ItemQueryResolver(
    val cursorUtil: CursorUtil,
    val service: ItemRepository,
    val logger: KLogger,
) : GraphQLQueryResolver {

    @AdminSecured
    suspend fun retrieveAllItem(
        first: Int,
        after: UUID? = null,
        env: DataFetchingEnvironment? = null
    ): Connection<Item>? {
        logger.warn { "+ItemQueryResolver->retrieveAllItem" }
        val edges: List<Edge<Item>> =
            service.onRetrieveItem(first = first, after = after, token = UUID.randomUUID())
                .map { DefaultEdge(it, cursorUtil.createCursorWith(it.id)) }
                .take(first)
        val pageInfo = DefaultPageInfo(
            cursorUtil.firstCursorFrom(edges),
            cursorUtil.lastCursorFrom(edges),
            after != null,
            edges.size >= first
        )
        return DefaultConnection(edges, pageInfo)
    }

    @AdminSecured
    suspend fun searchItems(
        input: String,
        first: Int,
        after: UUID? = null,
        env: DataFetchingEnvironment? = null
    ): Connection<Item>? {
        logger.warn { "+ItemQueryResolver->searchItems" }
        val edges: List<Edge<Item>> =
            service.search(input = input, first = first, after = after, token = UUID.randomUUID())
                .map { DefaultEdge(it, cursorUtil.createCursorWith(it.id)) }
                .take(first)
        val pageInfo = DefaultPageInfo(
            cursorUtil.firstCursorFrom(edges),
            cursorUtil.lastCursorFrom(edges),
            after != null,
            edges.size >= first
        )
        return DefaultConnection(edges, pageInfo)
    }


    @AdminSecured
    suspend fun retrieveItemByCategoryId(
        categoryId: UUID,
        first: Int,
        after: UUID? = null,
        env: DataFetchingEnvironment? = null
    ): Connection<Item>? {
        logger.warn { "+ItemQueryResolver->retrieveItemByCategoryId" }
        val edges: List<Edge<Item>> =
            service.itemsByCategoryId(categoryId = categoryId, first = first, after = after, token = UUID.randomUUID())
                .map { DefaultEdge(it, cursorUtil.createCursorWith(it.id)) }
                .take(first)
        val pageInfo = DefaultPageInfo(
            cursorUtil.firstCursorFrom(edges),
            cursorUtil.lastCursorFrom(edges),
            after != null,
            edges.size >= first
        )
        return DefaultConnection(edges, pageInfo)
    }


    @AdminSecured
    suspend fun retrieveAllItemHavingCategory(
        first: Int,
        after: UUID? = null,
        env: DataFetchingEnvironment? = null
    ): Connection<Item>? {
        logger.warn { "+ItemQueryResolver->retrieveAllItemHavingCategory" }

        val edges: List<Edge<Item>> =
            service.onRetrieveItemHavingCategory(first = first, after = after, token = UUID.randomUUID())
                .map { DefaultEdge(it, cursorUtil.createCursorWith(it.id)) }
                .take(first)
        val pageInfo = DefaultPageInfo(
            cursorUtil.firstCursorFrom(edges),
            cursorUtil.lastCursorFrom(edges),
            after != null,
            edges.size >= first
        )
        return DefaultConnection(edges, pageInfo)
    }


}