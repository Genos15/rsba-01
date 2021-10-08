package com.rsba.order_microservice.domain.model

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class ElkGraphData(val nodes: List<ElkGraphNode> = emptyList(), val links: List<ElkGraphLink> = emptyList()) {
    constructor(entries: List<Item.GraphItem>, height: Int, width: Int) : this(
        nodes = entries.map {
            ElkGraphNode(id = it.id, height = height, width = width, payload = it)
        }, links = entries.filter { it.parentId != null }.map {
            ElkGraphLink(id = UUID.randomUUID(), source = it.parentId, target = it.id)
        }
    )
}