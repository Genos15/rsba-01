package com.rsba.order_microservice.domain.security

 import com.rsba.order_microservice.domain.exception.CustomGraphQLError
import graphql.schema.DataFetchingEnvironment
import java.util.*
import kotlin.jvm.Throws

interface TokenAnalyzer {
    @Throws(RuntimeException::class, IllegalArgumentException::class, CustomGraphQLError::class)
    operator fun invoke(environment: DataFetchingEnvironment): UUID
}