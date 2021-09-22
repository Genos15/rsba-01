package com.rsba.gatewaydemo.domain.input

import com.rsba.gatewaydemo.domain.model.AbstractModel
import org.springframework.data.redis.core.RedisHash

@RedisHash(value = "LoginUserReturn")
data class LoginUserReturn(
    val token: String,
    val expireIn: String
) : AbstractModel {
    companion object {
        const val KEY = "LoginUserReturn"
    }
}
