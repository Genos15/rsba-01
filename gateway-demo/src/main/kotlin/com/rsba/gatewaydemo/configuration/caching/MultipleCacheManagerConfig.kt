package com.rsba.gatewaydemo.configuration.caching

import com.rsba.usermicroservice.utils.CacheHelper
import org.springframework.cache.CacheManager
import org.springframework.context.annotation.Configuration
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.annotation.Bean

@Configuration
class MultipleCacheManagerConfig {

    @Bean
    fun concurrentCacheManager(): CacheManager {
        return ConcurrentMapCacheManager(
            CacheHelper.TOKEN_CACHE_NAME,
            CacheHelper.USER_CACHE_NAME,
            CacheHelper.EMAIL_CACHE_NAME
        )
    }
}