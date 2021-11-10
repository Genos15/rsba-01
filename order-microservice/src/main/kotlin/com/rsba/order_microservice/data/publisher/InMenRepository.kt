package com.rsba.order_microservice.data.publisher

import org.reactivestreams.Publisher
import java.util.*

interface InMenRepository {
    fun getUUIDPublisher(): Publisher<UUID>
}