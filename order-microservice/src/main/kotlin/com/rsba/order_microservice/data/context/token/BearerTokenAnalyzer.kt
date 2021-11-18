package com.rsba.order_microservice.data.context.token

import com.rsba.order_microservice.data.context.CustomGraphQLContext
import com.rsba.order_microservice.domain.exception.CustomGraphQLError
import com.rsba.order_microservice.domain.security.TokenAnalyzer
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import java.util.*
import kotlin.jvm.Throws

@Component
class BearerTokenAnalyzer : TokenAnalyzer {

    @Throws(CustomGraphQLError::class)
    override fun invoke(environment: DataFetchingEnvironment): UUID {
        val context: CustomGraphQLContext = environment.getContext()
        val request = context.httpServletRequest
        val full = request.getHeader("Authorization") ?: ""
        if (!full.lowercase().contains("bearer")) {
            throw CustomGraphQLError("please add a bearer token")
        }
        val token = full.lowercase().replace("bearer ", "", ignoreCase = true)
        return try {
            UUID.fromString(token.trim())
        } catch (e: Exception) {
            throw CustomGraphQLError("ТОКЕН НЕ НАЙДЕН. ПОЖАЛУЙСТА, ЗАНЕСИТЕ В ЖУРНАЛ")
        }
    }

}