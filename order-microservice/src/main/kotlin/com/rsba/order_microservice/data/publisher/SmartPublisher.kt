package com.rsba.order_microservice.data.publisher

import graphql.schema.DataFetchingEnvironment
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks
import reactor.util.concurrent.Queues
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class SmartPublisher {

    private var monitorsSink: Sinks.Many<UserMessage> = Sinks.many()
        .multicast()
        .onBackpressureBuffer(Queues.SMALL_BUFFER_SIZE, false)

    private val sinkMap: ConcurrentMap<String, Sinks.Many<UserMessage>> = ConcurrentHashMap()

    private val monitors: Flux<UserMessage> = monitorsSink.asFlux()

    fun publish(topic: String, payload: UserMessage) = Flux.just(payload).doOnNext {
        val localSink = sinkMap[topic]
        //Now here I have to develop different implementation depending on the type of sink
        localSink?.tryEmitNext(it)?.orThrow()
    }

    fun get(topic: String, environment: DataFetchingEnvironment): Publisher<UserMessage> {

        if (!sinkMap.containsKey(topic)) {
            sinkMap[topic] = Sinks.many().unicast().onBackpressureBuffer()
        }

        return when (topic) {
            SinkStrategy.Monitors.topic -> {
                monitors
            }
            else -> sinkMap[topic]?.asFlux()
        } ?: Sinks.empty<UserMessage>().asMono()
    }
}