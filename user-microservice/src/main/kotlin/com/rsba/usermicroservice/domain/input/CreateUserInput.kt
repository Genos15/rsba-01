package com.rsba.usermicroservice.domain.input

import com.rsba.usermicroservice.domain.model.AbstractModel

data class CreateUserInput(
    val firstName: String,
    val lastName: String,
    val middleName: String? = null,
    val login: String,
    val password: String,
    val email: String,
    val phone: String? = null,
    val lang: String? = "ru",
    val code: String? = null
) : AbstractModel

