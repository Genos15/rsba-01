package  com.rsba.order_microservice.domain.exception

import   com.rsba.order_microservice.domain.error.ErrorSource
import graphql.ErrorClassification
import graphql.ErrorType
import graphql.GraphQLError
import graphql.language.SourceLocation

class DuplicateLoginKeyError : GraphQLError, ErrorFitterControl() {
    override fun getMessage(): String =
        ErrorSource(code = "0000", message = "IMPOSSIBLE TO DUPLICATE THE LOGIN. IT HAS TO BE UNIQUE").toString()

    override fun getLocations(): MutableList<SourceLocation> = mutableListOf()

    override fun getErrorType(): ErrorClassification = ErrorType.DataFetchingException

    override fun indices(): List<String> {
        return listOf("duplicate key value violates unique constraint", "users_login_uindex")
    }
}