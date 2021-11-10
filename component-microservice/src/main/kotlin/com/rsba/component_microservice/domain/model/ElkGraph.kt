package com.rsba.component_microservice.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ElkGraph<T : ElkGraphNode>(val nodes: List<T> = emptyList(), val links: List<ElkGraphLink> = emptyList())
