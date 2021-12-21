package com.rsba.tasks_microservice.configuration.security

import mu.KLogger
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class MyResponseFilter(val _logger: KLogger) : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        if ("POST".equals(other = request.method, ignoreCase = true)) {
            response.addHeader("this-is-a-login-request", "*")
        }

        if (request.headerNames.toList().contains("connection")) {
            chain.doFilter(request, response)
        } else {
            _logger.warn { request.headerNames.toList() }
            _logger.warn { "######## FILTER BEFORE REQUEST" }

            chain.doFilter(request, response)
        }
    }
}