package com.rsba.component_microservice.resolver.mutation

import  com.rsba.component_microservice.domain.input.*
import  com.rsba.component_microservice.aspect.AdminSecured
import com.rsba.component_microservice.domain.model.Technology
import com.rsba.component_microservice.domain.repository.TechnologyRepository
import com.rsba.component_microservice.domain.security.TokenAnalyzer
import graphql.kickstart.tools.GraphQLMutationResolver
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import java.util.*

@Component
class TechnologyMutation(private val service: TechnologyRepository, private val deduct: TokenAnalyzer) :
    GraphQLMutationResolver {

    @AdminSecured
    suspend fun createOrEditTechnology(
        input: CreateOrEditTechnologyInput,
        environment: DataFetchingEnvironment
    ): Optional<Technology> =
        service.createOrEdit(input = input, token = deduct(environment = environment))

    @AdminSecured
    suspend fun deleteTechnology(input: UUID, environment: DataFetchingEnvironment): Boolean =
        service.delete(input = input, token = deduct(environment = environment))

    @AdminSecured
    suspend fun unpinOperationInTechnology(
        input: List<TechnologyAndOperation>? = null,
        environment: DataFetchingEnvironment
    ): Optional<Technology> =
        service.unpinOperation(input = input ?: emptyList(), token = deduct(environment = environment))

    suspend fun importTechnologyFromJsonFile(environment: DataFetchingEnvironment): Optional<Boolean> =
        service.importTechnologyFromJsonFile(environment = environment)
}