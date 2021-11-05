package com.rsba.component_microservice.domain.exception

import graphql.GraphQLException
import graphql.kickstart.spring.error.ThrowableGraphQLError
import mu.KotlinLogging
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ExceptionHandler

@Component
class GraphQLExceptionHandler {

    val logger = KotlinLogging.logger {}

    @ExceptionHandler(GraphQLException::class, RuntimeException::class)
    fun handle(e: Exception): ThrowableGraphQLError {
        logger.warn { "@------- RuntimeException" }
        return ThrowableGraphQLError(e)
    }

    @ExceptionHandler(GraphQLException::class)
    fun handle(e: RuntimeException): ThrowableGraphQLError {
        logger.warn { "@------- GraphQLException" }
        return ThrowableGraphQLError(e, HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase)
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun handle(e: AccessDeniedException): ThrowableGraphQLError {
        logger.warn { "@------- AccessDeniedException" }
        return ThrowableGraphQLError(e, HttpStatus.FORBIDDEN.reasonPhrase)
    }

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handle(e: DataIntegrityViolationException): ThrowableGraphQLError {
        logger.warn { "@------- DataIntegrityViolationException" }
        return ThrowableGraphQLError(e, HttpStatus.FORBIDDEN.reasonPhrase)
    }

}