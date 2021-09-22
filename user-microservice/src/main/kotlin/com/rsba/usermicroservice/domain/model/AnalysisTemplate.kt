package com.rsba.usermicroservice.domain.model

import com.rsba.usermicroservice.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class AnalysisTemplate(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val content: String,
    val createdAt: String? = null,
    val editedAt: String? = null,
)