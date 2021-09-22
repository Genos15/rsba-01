package com.rsba.usermicroservice.domain.input

import com.rsba.usermicroservice.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class ManagerInGroupInput(
    @Serializable(with = UUIDSerializer::class) val groupId: UUID,
    @Serializable(with = UUIDSerializer::class) val userId: UUID,
)