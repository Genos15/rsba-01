package com.rsba.usermicroservice.interpector.aspect

 import com.rsba.usermicroservice.service.CacheService
 import mu.KLogger
 import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.core.annotation.Order
 import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException
 import kotlin.jvm.Throws
import org.springframework.web.context.request.ServletRequestAttributes

import org.springframework.web.context.request.RequestContextHolder
import java.util.*
import javax.servlet.http.HttpServletRequest


@Component
@Order(1)
@Aspect
class SecurityAspect(
    private val logger: KLogger,
    private val cached: CacheService
) {

//    @Throws(Throwable::class)
//    @Before("@annotation(Authorized)")
//    fun before() {
//    }

    @Around("@annotation(LoginSecured)")
    @Throws(RuntimeException::class, ResponseStatusException::class)
    fun authCheckingRegulator(joinPoint: ProceedingJoinPoint): Any? {
        return joinPoint.proceed()
//        logger.warn { "STEP 1" }
//        val signatureArgs = joinPoint.args
//        for (sg in signatureArgs) {
//            if (sg != null && sg is DataFetchingEnvironmentImpl) {
//                val context: CustomGraphQLContext? = sg.getContext()
//                val xLogin = context?.httpServletRequest?.getHeader("X-Method")
//                if (xLogin != null && xLogin.equals("login", ignoreCase = true)) {
//                    logger.warn { "+------ ANNOTATION = proceed to the request" }
//                    return joinPoint.proceed()
//                } else {
//                    break
//                }
//            }
//        }
//
//        throw ResponseStatusException(
//            HttpStatus.UNAUTHORIZED,
//            "IMPOSSIBLE TO LOG WITHOUT MANDATORY HEADERS",
//            AuthenticationException(HttpStatus.FORBIDDEN.reasonPhrase)
//        )
    }


    @Around("@annotation(AdminSecured)")
    @Throws(RuntimeException::class, ResponseStatusException::class)
    fun auth2(joinPoint: ProceedingJoinPoint): Any? {
        logger.warn { "STEP 1" }
        return joinPoint.proceed()
//        val signatureArgs = joinPoint.args
//
//        for (sg in signatureArgs) {
//            if (sg != null && sg is DataFetchingEnvironmentImpl) {
//                val context: CustomGraphQLContext? = sg.getContext()
//                val auth = context?.httpServletRequest?.getHeader("Authorization")
//                if (auth != null && auth.contains("Bearer ", ignoreCase = false)) {
//
//                    val key = auth.toLowerCase().replace("bearer", "").trim()
//
//                    val token = cached.getTokenHelper(key = key)
//
//                    logger.warn { "TOKEN HELPER = $token" }
//                    logger.warn { "+------ ANNOTATION 2 = proceed to the request" }
//
//                    if (token != null) {
//                        try {
//                            val time = token.expireIn ?: 0
//                            if (Instant.ofEpochMilli(time).isBefore(Instant.now())) {
//                                throw RuntimeException("≠------- TOKEN VALIDITY IS UP! REFRESH PLEASE")
//                            }
//                        } catch (e: Exception) {
//                            e.printStackTrace()
//                            throw ResponseStatusException(
//                                HttpStatus.UNAUTHORIZED,
//                                "TOKEN EXPIRED. PLEASE REFRESH YOUR TOKEN",
//                                AuthenticationException("NOT AUTHENTICATED")
//                            )
//                        }
//                        return joinPoint.proceed()
//                    }
//                }
//
//                continue
//            }
//        }
//
//        throw ResponseStatusException(
//            HttpStatus.UNAUTHORIZED,
//            "TOKEN NOT FOUND. PLEASE LOGIN",
//            AuthenticationException(HttpStatus.FORBIDDEN.reasonPhrase)
//        )
    }

//    @After("@annotation(LoginSecured)")
//    @Throws(Throwable::class)
//    fun afterLogin(joinPoint: JoinPoint) {
//        logger.warn { "+------ After Login from ASPECT" }
//        logger.warn { "+------ After Login from ASPECT" }
//
//        logger.warn { joinPoint.args }
//
//    }

    private fun currentRequest(): HttpServletRequest? {
        val servletRequestAttributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes?
        return Optional.ofNullable(servletRequestAttributes)
            .map { obj: ServletRequestAttributes -> obj.request }
            .orElse(null)
    }

}

