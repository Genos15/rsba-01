package  com.rsba.order_microservice.aspect

import mu.KLogger
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException
import kotlin.jvm.Throws

@Component
@Order(1)
@Aspect
class SecurityAspect(
    private val logger: KLogger,
) {

    @Around("@annotation(AdminSecured)")
    @Throws(RuntimeException::class, ResponseStatusException::class)
    fun broker(joinPoint: ProceedingJoinPoint): Any? {
        return joinPoint.proceed()
    }
}

