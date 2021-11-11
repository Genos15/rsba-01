package com.rsba.order_microservice.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class OrderCompletionLine(
    val created: Map<String, Int>,
    val delivered: Map<String, Int>,
    val done: Map<String, Int>,
)
