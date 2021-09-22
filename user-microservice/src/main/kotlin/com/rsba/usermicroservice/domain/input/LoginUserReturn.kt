package com.rsba.usermicroservice.domain.input

import com.rsba.usermicroservice.domain.model.AbstractModel
import kotlinx.serialization.Serializable

@Serializable
data class LoginUserReturn(
    val accessToken: String,
    val refreshToken: String,
    val expireIn: String
) : AbstractModel
