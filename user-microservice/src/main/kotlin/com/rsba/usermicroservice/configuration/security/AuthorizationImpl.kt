package com.rsba.usermicroservice.configuration.security


import com.rsba.usermicroservice.context.CustomGraphQLContext
import com.rsba.usermicroservice.domain.input.LoginUserReturn
import com.rsba.usermicroservice.domain.model.TokenHelper
import com.rsba.usermicroservice.service.CacheService
import org.springframework.stereotype.Component
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*
import io.jsonwebtoken.SignatureAlgorithm
import mu.KLogger
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cache.CacheManager

@Component
class AuthorizationImpl(
    private val logger: KLogger,
    @Qualifier("concurrentCacheManager") private var cache: CacheManager,
    private val service: CacheService
) {

    fun authorize(token: String?): Boolean {
        return true
    }

    fun authorize(context: CustomGraphQLContext?): Boolean {
        logger.warn { "token = ${tokenEncoder("", "")}" }
        if (context != null) {
            val headers = context.httpServletRequest.headerNames?.toList()?.map { it.toLowerCase() }
            if (headers?.contains("authorization") == true) {
                return true
            }
        }
        return false
    }

    @Throws(RuntimeException::class)
    fun tokenEncoder(login: String, password: String): LoginUserReturn {
        val secret = "RSBAPLUS&CYNAPSE"
        val expiredIn = Date.from(Instant.now().plus(1, ChronoUnit.HOURS))
        val claims: HashMap<String, Any?> = HashMap<String, Any?>();
        claims["iss"] = UUID.randomUUID()
        claims["sub"] = "LoginRequest"
        claims["login"] = login
        claims["password"] = password
        val jwt: String = Jwts.builder()
            .setClaims(claims)
            .signWith(SignatureAlgorithm.HS512, secret)
            .setExpiration(expiredIn)
            .compact()

        val jwsClaims: Jws<Claims>? = Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(jwt)

        try {
            val data = TokenHelper(
                login = login,
                password = password,
                token = jwt,
                expireIn = expiredIn.time,
                ref = UUID.randomUUID()
            )
            service.saveTokenHelper(instance = data)
            return LoginUserReturn(accessToken = data.ref.toString(),refreshToken = data.ref.toString(), expireIn = "${expiredIn.time}")
        } catch (e: Exception) {
            logger.warn { "error = ${e.message}" }
            throw RuntimeException("НЕВОЗМОЖНОСТЬ СГЕНЕРИРОВАТЬ ПОЛЬЗОВАТЕЛЬСКИЙ ТОКЕН")
        }
    }

    private fun tokenDecoder(jwt: String): Jws<Claims>? {
        val secret = "RSBAPLUS&CYNAPSE"
        return Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(jwt)
    }

}