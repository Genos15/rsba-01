package com.rsba.order_microservice.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class OrderStatus : AbstractStatus {
    DRAFT,
    SUSPENDED,
    ANALYZING,
    PRODUCTION,
    DELIVERED;

    companion object {
        fun fromString(src: String): OrderStatus = when (src.lowercase()) {
            "suspended" -> SUSPENDED
            "analyzing" -> ANALYZING
            "production" -> PRODUCTION
            "delivered" -> DELIVERED
            else -> DRAFT
        }
    }
}