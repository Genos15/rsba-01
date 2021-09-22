package com.rsba.usermicroservice.domain.model

import java.util.*

data class TokenHelper(val login: String, val password: String, val token: String, val expireIn: Long, val ref: UUID): AbstractModel