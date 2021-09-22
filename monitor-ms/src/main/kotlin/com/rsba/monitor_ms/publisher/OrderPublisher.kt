package com.rsba.monitor_ms.publisher

import com.rsba.monitor_ms.domain.model.Order
import mu.KLogger
import org.reactivestreams.Publisher
import org.springframework.stereotype.Component
import reactor.core.publisher.DirectProcessor
import reactor.core.publisher.FluxProcessor
import reactor.core.publisher.FluxSink

@Component
class OrderPublisher(private val logger: KLogger) {

    lateinit var processor: FluxProcessor<List<Order>, List<Order>>
    lateinit var sink: FluxSink<List<Order>>

    init {
        val processor: FluxProcessor<List<Order>, List<Order>> = DirectProcessor.create<List<Order>>().serialize()
        val sink: FluxSink<List<Order>> = processor.sink()
    }

    fun publish(order: List<Order>) = try {
        sink.next(order)
    } catch (e: Exception) {
        sink.complete()
    }

    fun get(): Publisher<List<Order>> {
        return processor.map {
            logger.warn { "+--- new elements from list of processors" }
            return@map it
        }
    }
}
