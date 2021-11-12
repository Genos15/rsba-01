package com.rsba.component_microservice.data.service.usecase.item_category

import com.rsba.component_microservice.data.dao.ItemCategoryDao
import com.rsba.component_microservice.data.service.usecase.queries.ItemCategoryQueries
import com.rsba.component_microservice.domain.queries.QueryCursor
import com.rsba.component_microservice.domain.model.ElkGraph
import com.rsba.component_microservice.domain.model.ElkGraphItemCategoryNode
import com.rsba.component_microservice.domain.model.ElkGraphLink
import com.rsba.component_microservice.domain.usecase.common.RetrieveFullElkGraphUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import java.util.*


@Component("item_category_elk")
@OptIn(ExperimentalSerializationApi::class)
class RetrieveFullElkGraphUseCaseImpl : RetrieveFullElkGraphUseCase {
    override suspend fun invoke(
        database: DatabaseClient,
        token: UUID,
        from: UUID?,
        height: Int,
        width: Int,
    ): ElkGraph<ElkGraphItemCategoryNode> =
        database.sql(ItemCategoryQueries.elk(token = token, from = from))
            .map { row -> QueryCursor.all(row = row) }
            .first()
            .map { it?.mapNotNull { element -> (element as? ItemCategoryDao?) } ?: emptyList() }
            .map { entries ->
                val nodes = entries.map { node ->
                    ElkGraphItemCategoryNode(
                        id = node.id,
                        height = height,
                        width = width,
                        node = node.to
                    )
                }
                val links: List<ElkGraphLink> = entries.flatMap { node ->
                    node.childrenIds?.mapNotNull {
                        try {
                            UUID.fromString(it)
                        } catch (e: IllegalArgumentException) {
                            null
                        }
                    }?.map {
                        ElkGraphLink(id = "", source = node.id, target = it)
                    }?.toList() ?: emptyList()
                }.mapIndexed { index, node ->
                    node.id = (index + 1).toString()
                    node
                }
                ElkGraph(nodes = nodes, links = links)
            }
            .onErrorResume {
                throw it
            }
            .log()
            .awaitFirstOrElse { ElkGraph() }
}