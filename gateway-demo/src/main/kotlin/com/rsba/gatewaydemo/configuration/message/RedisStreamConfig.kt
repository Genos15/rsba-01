package com.rsba.gatewaydemo.configuration.message

import mu.KLogger
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.data.redis.connection.stream.ObjectRecord

import org.springframework.data.redis.stream.StreamListener
import org.springframework.data.redis.connection.stream.ReadOffset
import org.springframework.data.redis.stream.StreamMessageListenerContainer

import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.stream.Consumer
import org.springframework.data.redis.connection.stream.StreamOffset
import org.springframework.data.redis.stream.StreamMessageListenerContainer.StreamMessageListenerContainerOptions
import org.springframework.data.redis.stream.Subscription
import java.net.UnknownHostException
import java.time.Duration
import java.net.InetAddress


@Configuration
class RedisStreamConfig(
    private val env: Environment,
    private val _logger: KLogger,
    private val streamListener: StreamListener<String, ObjectRecord<String, String>>
) {

    @Value("token-events")
    private val streamKey: String = ""

    @Bean
    @Throws(UnknownHostException::class)
    fun subscription(redisConnectionFactory: RedisConnectionFactory?): Subscription? {
        val options: StreamMessageListenerContainerOptions<String, ObjectRecord<String, String>> =
            StreamMessageListenerContainerOptions
                .builder()
                .pollTimeout(Duration.ofSeconds(1))
                .targetType(String::class.java)
                .build()
        val listenerContainer: StreamMessageListenerContainer<String, ObjectRecord<String, String>> =
            StreamMessageListenerContainer
                .create(redisConnectionFactory, options)
        val subscription = listenerContainer.receive(
            Consumer.from(streamKey, InetAddress.getLocalHost().hostName),
            StreamOffset.create(streamKey, ReadOffset.lastConsumed()),
            streamListener
        )
        listenerContainer.start()
        _logger.warn { "+---- creating configuration of subscriber" }
        return subscription
    }
}