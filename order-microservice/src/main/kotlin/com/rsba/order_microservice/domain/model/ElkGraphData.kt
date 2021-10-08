package com.rsba.order_microservice.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ElkGraphData(val nodes: List<ElkGraphNode> = emptyList(), val links: List<ElkGraphLink> = emptyList())