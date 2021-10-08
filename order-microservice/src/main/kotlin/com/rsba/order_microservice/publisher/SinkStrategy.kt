package com.rsba.order_microservice.publisher

sealed class SinkStrategy(val topic: String) {
    object messages : SinkStrategy(topic = "messages")
    object monitors : SinkStrategy(topic = "monitors")
}
