package com.rsba.usermicroservice.domain.input

import com.rsba.usermicroservice.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class UserInWorkingCenterPayload(
    @Serializable(with = UUIDSerializer::class) val workcenterId: UUID,
    @Serializable(with = UUIDSerializer::class) val userId: UUID,
    val isManager: Boolean = false
)