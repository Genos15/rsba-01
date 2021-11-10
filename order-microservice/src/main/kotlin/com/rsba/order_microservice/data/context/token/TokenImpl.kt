package com.rsba.order_microservice.data.context.token

import com.rsba.order_microservice.data.context.CustomGraphQLContext
import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.http.HttpServletRequest
import kotlin.jvm.Throws

@Component
class TokenImpl(private val logger: KLogger) {

    @Throws(RuntimeException::class, IllegalArgumentException::class)
    fun read(environment: DataFetchingEnvironment): UUID {
        val context: CustomGraphQLContext = environment.getContext()
        val request = context.httpServletRequest
        val full = request.getHeader("Authorization") ?: ""
        val token = full.lowercase().replace("bearer ", "", ignoreCase = true)
        return try {
            UUID.fromString(token)
        } catch (e: Exception) {
            logger.warn { "+TokenImpl -> read -> error = ${e.message}" }
            throw RuntimeException("TOKEN NOT FOUND. PLEASE LOG")
        }
    }

    fun read(request: HttpServletRequest?): UUID? {
        val full = request?.getHeader("Authorization") ?: ""
        val token = full.lowercase().replace("bearer ", "", ignoreCase = true)
        return try {
            UUID.fromString(token)
        } catch (e: Exception) {
            logger.warn { "+TokenImpl -> read -> error = ${e.message}" }
            null
        }
    }
}