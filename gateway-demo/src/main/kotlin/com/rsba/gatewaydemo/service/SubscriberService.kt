package com.rsba.gatewaydemo.service

import com.rsba.gatewaydemo.domain.input.LoginUserReturn
import com.rsba.gatewaydemo.domain.model.AbstractModel
import com.rsba.usermicroservice.utils.CacheHelper
import mu.KLogger
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cache.CacheManager
import org.springframework.data.redis.connection.stream.ObjectRecord
import org.springframework.data.redis.core.ReactiveRedisOperations
import org.springframework.stereotype.Service
import org.springframework.data.redis.stream.StreamListener
import org.springframework.scheduling.annotation.Scheduled
import java.net.InetAddress

import java.util.concurrent.atomic.AtomicInteger


@Service
class SubscriberService(
    private val _logger: KLogger,
    private val redisTemplate: ReactiveRedisOperations<String, String>,
    @Qualifier("concurrentCacheManager") private var cache: CacheManager,
) : StreamListener<String, ObjectRecord<String, String>> {

    private val atomicInteger = AtomicInteger(0)


    override fun onMessage(record: ObjectRecord<String, String>?) {
        _logger.warn { "+--------MESSAGE RECEIVED IN GATEWAY" }
        _logger.warn { "**********>|" + InetAddress.getLocalHost().hostName + " - Consumed :" + record?.value }

        if (record?.value != null) {
            this.redisTemplate
                .opsForZSet()
                .incrementScore(record.value, "LIKE", 1.0)
                .subscribe {}
        }

        if (record?.value == null) {
            this.redisTemplate
                .opsForZSet()
                .incrementScore("SOMETHING", record?.value ?: "DISLIKE", 1.0)
                .subscribe {}
        }

        try {
            record?.value?.let {
                val result = AbstractModel.fromJson(it, LoginUserReturn::class.java)
                val done = cache.getCache(CacheHelper.TOKEN_CACHE_NAME)?.putIfAbsent(result.token, result)
                _logger.warn { "TOKEN ADDED IN CACHE = ${result.token}" }
                _logger.warn { "WRAPPER = $done" }
            }
        } catch (e: Exception) {
            _logger.warn { "error = ${e.message}" }
        }

        atomicInteger.incrementAndGet()
    }

    @Scheduled(fixedRate = 60_000)
    fun showPublishedEventsSoFar() {
        _logger.warn { "Total Consumer :: " + atomicInteger.get() }
    }


}