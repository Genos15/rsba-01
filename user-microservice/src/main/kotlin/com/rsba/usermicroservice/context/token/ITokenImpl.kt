package com.rsba.usermicroservice.context.token

import com.rsba.usermicroservice.context.CustomGraphQLContext
import graphql.schema.DataFetchingEnvironment
import java.util.*
import kotlin.jvm.Throws

interface ITokenImpl {

    @Throws(RuntimeException::class, IllegalArgumentException::class)
    fun readToken(environment: DataFetchingEnvironment): UUID {
        val context: CustomGraphQLContext = environment.getContext()
        val request = context.httpServletRequest
        val full = request.getHeader("Authorization") ?: ""
        val token = full.lowercase().replace("bearer ", "", ignoreCase = true)
        return try {
            UUID.fromString(token)
        } catch (e: Exception) {
            println("+TokenImpl->read->error = ${e.message}")
            throw RuntimeException("ТОКЕН НЕ НАЙДЕН. ПОЖАЛУЙСТА, ЗАНЕСИТЕ В ЖУРНАЛ")
        }
    }

}