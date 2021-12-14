package com.rsba.parameters_microservice.resolver.mutation

import  com.rsba.parameters_microservice.domain.input.*
import com.rsba.parameters_microservice.domain.model.MutationAction
import com.rsba.parameters_microservice.domain.model.Parameter
import com.rsba.parameters_microservice.domain.repository.ParameterRepository
import com.rsba.parameters_microservice.domain.security.TokenAnalyzer
import graphql.kickstart.tools.GraphQLMutationResolver
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import java.util.*

@Component
class ParameterMutation(private val service: ParameterRepository, private val deduct: TokenAnalyzer) :
    GraphQLMutationResolver {

    suspend fun createOrEditParameter(
        input: ParameterInput,
        action: MutationAction? = null,
        environment: DataFetchingEnvironment
    ): Optional<Parameter> =
        service.toCreateOrEdit(input = input, token = deduct(environment = environment), action = action)

    suspend fun deleteParameter(input: UUID, environment: DataFetchingEnvironment): Boolean =
        service.toDelete(input = input, token = deduct(environment = environment))

}