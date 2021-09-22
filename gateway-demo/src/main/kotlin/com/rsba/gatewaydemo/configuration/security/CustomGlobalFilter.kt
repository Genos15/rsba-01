package com.rsba.gatewaydemo.configuration.security

import com.rsba.gatewaydemo.domain.input.LoginUserReturn
import com.rsba.usermicroservice.utils.CacheHelper
import mu.KotlinLogging
import org.apache.http.auth.AuthenticationException
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cache.CacheManager
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.lang.Exception
import java.time.Instant
import kotlin.jvm.Throws

@Component
class CustomGlobalFilter(
    @Qualifier("concurrentCacheManager") private var cache: CacheManager,
) : GlobalFilter {

    private val logger = KotlinLogging.logger { }

    @Throws(RuntimeException::class, ResponseStatusException::class)
    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {

        val request = exchange.request
        val auth = request.headers["Authorization"]
        val xMethod = request.headers["X-Method"]

        val signature = auth?.firstOrNull()

        val port = request.uri.port
        val path = request.uri.path
        val method = request.methodValue

//        if (port == 9000 &&
//            path.toLowerCase().contains("playground", ignoreCase = true) ||
//            method.toLowerCase().equals("get", ignoreCase = true)
//        ) {
//
//            logger.warn { "JUST PROCEED" }
//
//        } else {
//
//            if ((signature == null
//                        || signature.isEmpty()
//                        || !signature.toLowerCase().contains("bearer")) && (xMethod == null || xMethod.isEmpty())
//            ) {
//                throw ResponseStatusException(
//                    HttpStatus.UNAUTHORIZED,
//                    "TOKEN NOT FOUND. PLEASE LOGIN",
//                    AuthenticationException("NOT AUTHENTICATED")
//                )
//            }
//
//            if (signature != null && xMethod?.firstOrNull() == null) {
//                val key = signature.toLowerCase().replace("bearer", "").trim()
//                logger.warn { "KEY = $key" }
//                logger.warn { cache.cacheNames }
//                val token = cache.getCache(CacheHelper.TOKEN_CACHE_NAME)?.get(key, LoginUserReturn::class.java)
//                logger.warn { "TOKEN = $token" }
//                if (token == null) {
//                    throw ResponseStatusException(
//                        HttpStatus.UNAUTHORIZED,
//                        "TOKEN NOT FOUND. PLEASE LOGIN",
//                        AuthenticationException("NOT AUTHENTICATED")
//                    )
//                } else {
//                    try {
//                        val time = token.expireIn.toLong()
//                        if (Instant.ofEpochMilli(time).isBefore(Instant.now())) {
//                            throw RuntimeException("≠------- TOKEN VALIDITY IS UP! REFRESH PLEASE")
//                        }
//                    } catch (e: Exception) {
//                        e.printStackTrace()
//                        throw ResponseStatusException(
//                            HttpStatus.UNAUTHORIZED,
//                            "TOKEN EXPIRED. PLEASE REFRESH YOUR TOKEN",
//                            AuthenticationException("NOT AUTHENTICATED")
//                        )
//                    }
//                }
//            } else if (xMethod?.firstOrNull() != null && xMethod.firstOrNull()?.toLowerCase()
//                    ?.equals("login", ignoreCase = true) == true
//            ) {
//                logger.warn { "LOGIN PROCEED…" }
//            }
//        }


        return chain.filter(exchange)

    }
}