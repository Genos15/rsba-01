package com.rsba.usermicroservice.service

import com.rsba.usermicroservice.domain.input.SingleInviteUserReturn
import com.rsba.usermicroservice.domain.model.TokenHelper
import com.rsba.usermicroservice.utils.CacheHelper
import mu.KLogger
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cache.CacheManager
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*
import kotlin.Exception

@Service
class CacheService(
    private val _logger: KLogger,
    @Qualifier("concurrentCacheManager") private val cached: CacheManager
) {

    fun saveInvitation(invitation: SingleInviteUserReturn): Boolean = try {
        cached.getCache(CacheHelper.EMAIL_CACHE_NAME)?.putIfAbsent(invitation.code, invitation)
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }

    fun saveTokenHelper(instance: TokenHelper) = try {
        Flux.just(instance)
            .doOnNext {
                cached.getCache(CacheHelper.TOKEN_CACHE_NAME)?.put(it.ref.toString(), it)
            }.doFinally {
                cached.getCache(CacheHelper.TOKEN_CACHE_KEY)?.put(instance.login, instance)
            }.subscribe { _logger.warn { "+-------- SAVED TOKEN" } }
    } catch (e: Exception) {
        _logger.warn { e.message }
    }

    fun removeTokenHelper(key: String): Boolean {
        return cached.getCache(CacheHelper.TOKEN_CACHE_NAME)?.evictIfPresent(key) ?: false
    }

    fun getTokenHelper(key: String): TokenHelper? = try {
        cached.getCache(CacheHelper.TOKEN_CACHE_NAME)?.get(key, TokenHelper::class.java)
    } catch (e: Exception) {
        _logger.warn { "user-ms/CacheService/getTokenHelper/error = ${e.message}" }
        null
    }

    fun <T> getAsync(key: String, _class: Class<T>, source: String): Mono<Optional<T>> = Mono.just(key)
        .map { Optional.ofNullable(cached.getCache(source)?.get(it, _class)) }
        .onErrorResume { t ->
            _logger.warn { t.message }
            return@onErrorResume Mono.empty()
        }

    fun getInvitation(code: String): SingleInviteUserReturn? = try {
        cached.getCache(CacheHelper.EMAIL_CACHE_NAME)?.get(code, SingleInviteUserReturn::class.java)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }


}