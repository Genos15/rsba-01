package com.rsba.order_microservice.publisher

sealed class SinkStrategy(val topic: String) {
    object Messages : SinkStrategy(topic = "messages")
    object Monitors : SinkStrategy(topic = "monitors")
}
