package com.rsba.component_microservice.resolver.mutation

import  com.rsba.component_microservice.domain.input.*
import com.rsba.component_microservice.domain.model.Operation
import  com.rsba.component_microservice.aspect.AdminSecured
import com.rsba.component_microservice.context.token.TokenImpl
import com.rsba.component_microservice.repository.OperationRepository
import graphql.kickstart.tools.GraphQLMutationResolver
import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import org.springframework.stereotype.Component
import java.util.*

@Component
class OperationMutation(
    private val service: OperationRepository,
    private val logger: KLogger,
    private val tokenImpl: TokenImpl
) :
    GraphQLMutationResolver {

    @AdminSecured
    suspend fun createOrEditOperation(
        input: CreateOrEditOperationInput,
        environment: DataFetchingEnvironment
    ): Optional<Operation> {
        logger.warn { "+OperationMutation -> createOrEditOperation" }
        return service.createOrEditOperation(input = input, token = tokenImpl.read(environment = environment))
    }

    @AdminSecured
    suspend fun deleteOperation(input: UUID, environment: DataFetchingEnvironment): Int {
        logger.warn { "+OperationMutation -> deleteOperation" }
        return service.deleteOperation(input = input, token = tokenImpl.read(environment = environment))
    }

    @AdminSecured
    suspend fun attachOperationToGroup(
        input: OperationAndGroupInput,
        environment: DataFetchingEnvironment
    ): Optional<Operation> {
        logger.warn { "+OperationMutation -> attachOperationToGroup" }
        return service.attachOperationToGroup(input = input, token = tokenImpl.read(environment = environment))
    }

    @AdminSecured
    suspend fun detachOperationToGroup(
        input: OperationAndGroupInput,
        environment: DataFetchingEnvironment
    ): Optional<Operation> {
        logger.warn { "+OperationMutation -> detachOperationToGroup" }
        return service.detachOperationToGroup(input = input, token = tokenImpl.read(environment = environment))
    }

    suspend fun importOperationFromJsonFile(environment: DataFetchingEnvironment): Optional<Boolean> {
        return service.importOperationFromJsonFile(environment = environment)
    }
}