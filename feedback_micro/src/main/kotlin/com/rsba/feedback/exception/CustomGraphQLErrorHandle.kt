package com.rsba.feedback.exception


import graphql.GraphQLError
import graphql.kickstart.execution.error.GraphQLErrorHandler
import org.springframework.stereotype.Component


@Component
class CustomGraphQLErrorHandle : GraphQLErrorHandler {
    override fun processErrors(errors: MutableList<GraphQLError>?): MutableList<GraphQLError> {
        return errors ?: mutableListOf()
    }
}