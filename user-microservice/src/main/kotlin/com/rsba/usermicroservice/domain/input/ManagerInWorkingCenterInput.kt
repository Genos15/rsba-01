package com.rsba.usermicroservice.domain.input

import com.rsba.usermicroservice.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class ManagerInWorkingCenterInput(
    @Serializable(with = UUIDSerializer::class) val workingCenterId: UUID,
    @Serializable(with = UUIDSerializer::class) val userId: UUID,
)