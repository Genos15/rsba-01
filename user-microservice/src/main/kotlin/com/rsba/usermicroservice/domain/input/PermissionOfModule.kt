package com.rsba.usermicroservice.domain.input

import com.rsba.usermicroservice.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class PermissionOfModule(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val name: String,
    val canCreate: Boolean,
    val canEdit: Boolean,
    val canView: Boolean,
    val canDelete: Boolean,
)