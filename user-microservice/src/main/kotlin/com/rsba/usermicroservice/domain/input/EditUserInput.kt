package com.rsba.usermicroservice.domain.input

import com.rsba.usermicroservice.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class EditUserInput(
    val firstname: String? = null,
    val lastname: String? = null,
    val middlename: String? = null,
    val login: String? = null,
    val password: String? = null,
    val newPassword: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val gender: String? = null,
    @Serializable(with = UUIDSerializer::class) var photo: UUID? = null
)