package com.rsba.component_microservice.resolver.mutation

import  com.rsba.component_microservice.domain.input.*
import  com.rsba.component_microservice.aspect.AdminSecured
import com.rsba.component_microservice.context.token.TokenImpl
import com.rsba.component_microservice.domain.model.Technology
import com.rsba.component_microservice.repository.TechnologyRepository
import graphql.kickstart.tools.GraphQLMutationResolver
import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import org.springframework.stereotype.Component
import java.util.*

@Component
class TechnologyMutation(
    private val service: TechnologyRepository,
    private val logger: KLogger,
    private val tokenImpl: TokenImpl
) : GraphQLMutationResolver {

    @AdminSecured
    suspend fun createOrEditTechnology(
        input: CreateOrEditTechnologyInput,
        environment: DataFetchingEnvironment
    ): Optional<Technology> {
        logger.warn { "+TechnologyMutation->createOrEdit" }
        return service.createOrEdit(input = input, token = tokenImpl.read(environment = environment))
    }

    @AdminSecured
    suspend fun deleteTechnology(input: UUID, environment: DataFetchingEnvironment): Boolean {
        logger.warn { "+TechnologyMutation->delete" }
        return service.delete(input = input, token = tokenImpl.read(environment = environment))
    }

    @AdminSecured
    suspend fun unpinOperationInTechnology(
        input: List<TechnologyAndOperation>? = null,
        environment: DataFetchingEnvironment
    ): Optional<Technology> {
        logger.warn { "+TechnologyMutation->unpinOperationInTechnology" }
        return service.unpinOperation(input = input ?: emptyList(), token = tokenImpl.read(environment = environment))
    }

    suspend fun importTechnologyFromJsonFile(environment: DataFetchingEnvironment): Optional<Boolean> {
        return service.importTechnologyFromJsonFile(environment = environment)
    }
}