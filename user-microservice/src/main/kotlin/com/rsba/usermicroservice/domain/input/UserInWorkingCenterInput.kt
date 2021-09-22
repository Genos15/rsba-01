package com.rsba.usermicroservice.domain.input

import com.rsba.usermicroservice.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class UserInWorkingCenterInput(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val users: List<String>? = listOf()
)