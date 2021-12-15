package com.rsba.order_microservice.resolver.mutation

import com.rsba.order_microservice.aspect.AdminSecured
import com.rsba.order_microservice.data.context.token.ITokenImpl
import com.rsba.order_microservice.domain.input.ParameterInput
import com.rsba.order_microservice.domain.repository.ParameterRepository
import graphql.kickstart.tools.GraphQLMutationResolver
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import java.util.*
import com.rsba.order_microservice.domain.model.Parameter

@Component
class ParameterMutation(private val service: ParameterRepository) : GraphQLMutationResolver, ITokenImpl {
    @AdminSecured
    suspend fun createOrEditParameter(
        input: ParameterInput,
        environment: DataFetchingEnvironment
    ): Optional<Parameter> = service.createOrEdit(input = input, token = readToken(environment = environment))

    @AdminSecured
    suspend fun addOrRemovePotentialValueToParameter(
        input: ParameterInput,
        environment: DataFetchingEnvironment
    ): Optional<Parameter> =
        service.addOrRemovePotentialValue(input = input, token = readToken(environment = environment))

    @AdminSecured
    suspend fun deleteParameter(input: UUID, environment: DataFetchingEnvironment): Boolean =
        service.delete(input = input, token = readToken(environment = environment))
}