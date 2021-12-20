package com.rsba.parameters_microservice.configuration.caching

import org.springframework.context.annotation.Configuration
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.cache.CacheManager
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.annotation.Bean

@Configuration
class MultipleCacheManagerConfig : CachingConfigurerSupport() {

    @Bean
    fun concurrentCacheManager(): CacheManager {
        return ConcurrentMapCacheManager("USER-CACHED-RSBA")
    }
}