package com.rsba.order_microservice.domain.model

import com.rsba.order_microservice.deserializer.UUIDSerializer

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Group(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val name: String,
    val description: String? = null,
    val createdAt: String? = null,
    val editedAt: String? = null,
    val parent: Group? = null,
    val priority: Int? = 0,
    val isAnalytic: Boolean? = null,
    val isStaging: Boolean? = null,
)