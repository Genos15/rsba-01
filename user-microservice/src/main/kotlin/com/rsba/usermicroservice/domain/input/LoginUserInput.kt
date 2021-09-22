package com.rsba.usermicroservice.domain.input

import com.rsba.usermicroservice.domain.model.AbstractModel

data class LoginUserInput(
    val login: String,
    val password: String
) : AbstractModel
