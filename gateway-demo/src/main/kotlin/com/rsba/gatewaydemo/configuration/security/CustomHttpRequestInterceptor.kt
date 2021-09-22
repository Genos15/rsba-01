package com.rsba.gatewaydemo.configuration.security

import mu.KotlinLogging
import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse
import org.springframework.stereotype.Component
import java.io.IOException

@Component
class CustomHttpRequestInterceptor : ClientHttpRequestInterceptor {

    private val logger = KotlinLogging.logger { }

    @Throws(IOException::class)
    override fun intercept(
        request: HttpRequest,
        body: ByteArray,
        execution: ClientHttpRequestExecution
    ): ClientHttpResponse {

        logger.warn { "≠------ Executing Request from GATEWAY" }

//        val headers: HttpHeaders = request.headers
//        headers.add(UserContext.CORRELATION_ID, UserContextHolder.getContext().getCorrelationId())
//        headers.add(UserContext.AUTH_TOKEN, UserContextHolder.getContext().getAuthToken())

        return execution.execute(request, body)

    }
}