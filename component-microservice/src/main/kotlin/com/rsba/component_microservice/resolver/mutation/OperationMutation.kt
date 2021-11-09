package com.rsba.component_microservice.resolver.mutation

import  com.rsba.component_microservice.domain.input.*
import com.rsba.component_microservice.domain.model.Operation
import  com.rsba.component_microservice.aspect.AdminSecured
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
        input: CreateOrEditOperationInput,
        environment: DataFetchingEnvironment
    ): Optional<Operation> = service.createOrEditOperation(input = input, token = deduct(environment = environment))

    @AdminSecured
    suspend fun deleteOperation(input: UUID, environment: DataFetchingEnvironment): Int =
        service.deleteOperation(input = input, token = deduct(environment = environment))

    @AdminSecured
    suspend fun attachOperationToGroup(
        input: OperationAndGroupInput,
        environment: DataFetchingEnvironment
    ): Optional<Operation> = service.attachOperationToGroup(input = input, token = deduct(environment = environment))

    @AdminSecured
    suspend fun detachOperationToGroup(
        input: OperationAndGroupInput,
        environment: DataFetchingEnvironment
    ): Optional<Operation> = service.detachOperationToGroup(input = input, token = deduct(environment = environment))

    suspend fun importOperationFromJsonFile(environment: DataFetchingEnvironment): Optional<Boolean> {
        return service.importOperationFromJsonFile(environment = environment)
    }
}