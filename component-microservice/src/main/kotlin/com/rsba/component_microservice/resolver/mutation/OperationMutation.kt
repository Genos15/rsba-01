package com.rsba.component_microservice.resolver.mutation

import  com.rsba.component_microservice.domain.input.*
import  com.rsba.component_microservice.aspect.AdminSecured
import com.rsba.component_microservice.domain.model.*
import com.rsba.component_microservice.domain.repository.OperationRepository
import com.rsba.component_microservice.domain.security.TokenAnalyzer
import graphql.kickstart.tools.GraphQLMutationResolver
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import java.util.*

@Component
class OperationMutation(private val service: OperationRepository, private val deduct: TokenAnalyzer) :
    GraphQLMutationResolver {

    @AdminSecured
    suspend fun createOrEditOperation(
        input: OperationInput,
        action: MutationAction? = null,
        case: OperationEdition? = null,
        environment: DataFetchingEnvironment
    ): Optional<Operation> =
        service.toCreateOrEdit(input = input, token = deduct(environment = environment), action = action, case = case)

    @AdminSecured
    suspend fun deleteOperation(input: UUID, environment: DataFetchingEnvironment): Boolean =
        service.toDelete(input = input, token = deduct(environment = environment))

}