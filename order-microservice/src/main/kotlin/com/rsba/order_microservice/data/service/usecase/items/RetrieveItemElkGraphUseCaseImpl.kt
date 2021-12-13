package com.rsba.order_microservice.data.service.usecase.items


import com.rsba.order_microservice.data.dao.ItemDao
import com.rsba.order_microservice.data.service.usecase.queries.ItemQueries
import com.rsba.order_microservice.domain.model.ElkGraph
import com.rsba.order_microservice.domain.model.ElkGraphItemNode
import com.rsba.order_microservice.domain.model.ElkGraphLink
import com.rsba.order_microservice.domain.queries.QueryCursor
import com.rsba.order_microservice.domain.usecase.custom.item.RetrieveItemElkGraphUseCase
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import java.util.*


@Component("item_elk")
@OptIn(ExperimentalSerializationApi::class)
class RetrieveItemElkGraphUseCaseImpl : RetrieveItemElkGraphUseCase {
    override suspend fun invoke(
        database: DatabaseClient,
        token: UUID,
        from: UUID?,
        orderId: UUID?,
        height: Int,
        width: Int,
    ): ElkGraph<ElkGraphItemNode> =
        database.sql(ItemQueries.elk(token = token, from = from, orderId = orderId))
            .map { row -> QueryCursor.all(row = row) }
            .first()
            .map { it?.mapNotNull { element -> (element as? ItemDao?) } ?: emptyList() }
            .map { entries ->
                val nodes = entries.map { node ->
                    ElkGraphItemNode(
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