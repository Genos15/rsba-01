package com.rsba.parameters_microservice.resolver.query

import com.rsba.parameters_microservice.domain.model.Parameter
import com.rsba.parameters_microservice.domain.repository.ParameterRepository
import com.rsba.parameters_microservice.domain.security.TokenAnalyzer
import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.relay.Connection
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import java.util.*

@Component
class ParameterQueryResolver(val service: ParameterRepository, private val deduct: TokenAnalyzer) :
    GraphQLQueryResolver, GenericRetrieveConnection {

    suspend fun retrieveParameters(
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<Parameter> = perform(
        entries = service.retrieve(
            first = first,
            after = after,
            token = deduct(environment = environment)
        ),
        first = first,
        after = after
    )

    suspend fun searchParameters(
        input: String,
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<Parameter> = perform(
        entries = service.search(
            input = input,
            first = first,
            after = after,
            token = deduct(environment = environment)
        ),
        first = first,
        after = after
    )

    suspend fun findParameter(id: UUID, environment: DataFetchingEnvironment): Optional<Parameter> =
        service.find(id = id, token = deduct(environment = environment))

//    suspend fun retrieveParametersValues(
//        id: UUID,
//        first: Int,
//        after: UUID? = null,
//        environment: DataFetchingEnvironment
//    ): Connection<String> = perform(
//        entries = service.values(
//            ids = setOf(id),
//            token = deduct(environment = environment),
//            first = first,
//            after = after
//        ),
//        first = first,
//        after = after,
//        id = id
//    )

    suspend fun countParameters(environment: DataFetchingEnvironment): Int =
        service.count(token = deduct(environment = environment))
}