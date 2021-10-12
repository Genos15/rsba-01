package com.rsba.usermicroservice.domain.input

import com.rsba.usermicroservice.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class EditUserByMasterInput(
    @Serializable(with = UUIDSerializer::class) var id: UUID,
    val blocked: Boolean? = null,
    @Serializable(with = UUIDSerializer::class) val roleId: UUID? = null,
)