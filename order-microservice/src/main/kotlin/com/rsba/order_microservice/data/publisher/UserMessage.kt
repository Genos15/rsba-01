package com.rsba.order_microservice.data.publisher

import java.util.*

data class UserMessage(
    val senderId: UUID = UUID.randomUUID(),
    val receiverId: UUID = UUID.randomUUID(),
    val message: String = ""
)