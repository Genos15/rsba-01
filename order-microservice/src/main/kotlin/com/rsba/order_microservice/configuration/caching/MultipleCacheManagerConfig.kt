package  com.rsba.order_microservice.configuration.caching

import  com.rsba.order_microservice.utils.CacheHelper
import org.springframework.context.annotation.Configuration
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.cache.CacheManager
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.annotation.Bean

@Configuration
class MultipleCacheManagerConfig : CachingConfigurerSupport() {

    @Bean
    fun concurrentCacheManager(): CacheManager {
        return ConcurrentMapCacheManager(
            CacheHelper.TOKEN_CACHE_NAME,
            CacheHelper.USER_CACHE_NAME,
            CacheHelper.EMAIL_CACHE_NAME
        )
    }
}