package com.rsba.order_microservice.data.context.token

import com.rsba.order_microservice.data.context.CustomGraphQLContext
import com.rsba.order_microservice.domain.exception.CustomGraphQLError
import graphql.schema.DataFetchingEnvironment
import java.util.*
import kotlin.jvm.Throws

interface ITokenImpl {

    @Throws(RuntimeException::class, IllegalArgumentException::class)
    fun readToken(environment: DataFetchingEnvironment): UUID {
        val context: CustomGraphQLContext = environment.getContext()
        val request = context.httpServletRequest
        val full = request.getHeader("Authorization") ?: ""
        if (!full.lowercase().contains("bearer")) {
            throw CustomGraphQLError("PLEASE ADD A BEARER TOKEN")
        }
        val token = full.lowercase().replace("bearer ", "", ignoreCase = true)
        return try {
            UUID.fromString(token)
        } catch (e: Exception) {
            println("+TokenImpl->read->error = ${e.message}")
            throw RuntimeException("ТОКЕН НЕ НАЙДЕН. ПОЖАЛУЙСТА, ЗАНЕСИТЕ В ЖУРНАЛ")
        }
    }

}