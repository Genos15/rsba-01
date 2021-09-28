package com.rsba.usermicroservice.domain.input

import com.rsba.usermicroservice.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class EditUserInput(
    val firstName: String? = null,
    val lastName: String? = null,
    val middleName: String? = null,
    val login: String? = null,
    val password: String? = null,
    val newPassword: String? = null,
    val email: String? = null,
    val phone: String? = null,
    @Serializable(with = UUIDSerializer::class) var photo: UUID? = null
)