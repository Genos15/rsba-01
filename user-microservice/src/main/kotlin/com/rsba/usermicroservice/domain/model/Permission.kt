package com.rsba.usermicroservice.domain.model

import com.rsba.usermicroservice.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Permission(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val name: String,
    val description: String? = null,
    val createdAt: String? = null,
    val editedAt: String? = null,
    @Serializable(with = UUIDSerializer::class) val moduleId: UUID? = null,
) : AbstractModel