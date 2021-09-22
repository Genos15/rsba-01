package com.rsba.usermicroservice.configuration.logging

import com.rsba.usermicroservice.domain.model.CachedUserContact
import com.rsba.usermicroservice.domain.model.CachedUserToInvite
import com.rsba.usermicroservice.utils.CacheHelper
import com.rsba.usermicroservice.utils.CodeUtils
import mu.KLogger
import org.apache.commons.validator.routines.EmailValidator
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cache.CacheManager
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class EmailHelper(
    @Qualifier("concurrentCacheManager") private var cache: CacheManager,
    private val emailValidator: EmailValidator,
    private val template: RestTemplate,
    private val logger: KLogger,
    private val env: Environment
) {


    //    "http://165.227.131.116:9003"
//    "http://192.168.0.101:9003"

    fun isValid(email: String): Boolean {
        return emailValidator.isValid(email)
    }

    fun sendConfirmation(username: String, email: String, companyName: String) = try {
        val uriLocal = "${env.getProperty("spring.cloud.consul.host")}:9000"

        val code = CodeUtils.forAccountConfirmation(5)
        logger.warn { "ID = $username, Email = $email" }
        val value = CachedUserContact(login = username, email = email)
        cache.getCache(CacheHelper.EMAIL_CACHE_NAME)?.put(code, value)
//        val notifierUri = "http://165.227.131.116:9000/notification-microservice"

        val notifierUri = "http://$uriLocal/notification-microservice"
        val uri = "$notifierUri/email/confirm?email=$email&code=$code&company=$companyName"
        logger.warn { "send on = $uri" }
        template.getForObject(uri, Any::class.java)
    } catch (e: Exception) {
        logger.warn { "≠----- error = $e" }
    }

    fun sendInvitation(email: String, companyName: String) = try {
        val uriLocal = "${env.getProperty("spring.cloud.consul.host")}:9000"

        val code = CodeUtils.forAccountConfirmation(5)
        val value = CachedUserToInvite(company = companyName, email = email)
        cache.getCache(CacheHelper.EMAIL_CACHE_NAME)?.put(code, value)
//        val notifierUri = "http://165.227.131.116:9000/notification-microservice"
//        val notifierUri = "http://localhost:9000/notification-microservice"
        val notifierUri = "http://$uriLocal/notification-microservice"

        val uri = "$notifierUri/email/invite?email=$email&code=$code&company=$companyName"

        logger.warn { "send on = $uri" }
        template.getForObject(uri, Any::class.java)
    } catch (e: Exception) {
        logger.warn { "≠----- error = $e" }
    }

    fun verifyEmailFromCode(email: String, code: String): CachedUserContact? = try {
        cache.getCache(CacheHelper.EMAIL_CACHE_NAME)
            ?.get(code, CachedUserContact::class.java)
    } catch (e: Exception) {
        logger.warn { "≠------ error = ${e.message}" }
        null
    }
}