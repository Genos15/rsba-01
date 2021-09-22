package com.rsba.usermicroservice.domain.input

import com.rsba.usermicroservice.deserializer.UUIDSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class ModuleWithPermission(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    @Serializable(with = UUIDSerializer::class) @SerialName(value = "roleid") val roleId: UUID,
    val name: String,
    val description: String? = null,
    val permissions: MutableList<PermissionOfModule>? = mutableListOf()
)