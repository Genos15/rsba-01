package com.rsba.component_microservice.domain.exception


import graphql.GraphQLError
 import graphql.kickstart.execution.error.GraphQLErrorHandler
import mu.KLogger
import org.springframework.stereotype.Component


@Component
class CustomGraphQLErrorHandle(private val logger: KLogger) : GraphQLErrorHandler {
    override fun processErrors(errors: MutableList<GraphQLError>?): MutableList<GraphQLError> {

//        val errorList = mutableListOf<GraphQLError>()
//        for (error in errors!!) {
//            val myMessage = error.message.split("]").last()
//            when (error.errorType) {
//                ErrorType.DataFetchingException -> {
//                    errorList.add(MyErrorGuesser.guess(myMessage))
//                }
//                else -> {
//                    errorList.add(GenericGraphQLError(error.message))
//                }
//            }
//        }
//        return errorList
//        return errors
        return errors ?: mutableListOf()
    }

}