package com.rsba.order_microservice.publisher

import org.reactivestreams.Publisher
import java.util.*

interface InMenRepository {
    fun getUUIDPublisher(): Publisher<UUID>
}