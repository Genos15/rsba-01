package com.rsba.component_microservice.resolver.mutation

import  com.rsba.component_microservice.domain.input.*
import  com.rsba.component_microservice.aspect.AdminSecured
import com.rsba.component_microservice.domain.model.MutationAction
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
        input: TechnologyInput,
        action: MutationAction? = null,
        environment: DataFetchingEnvironment
    ): Optional<Technology> =
        service.toCreateOrEdit(input = input, token = deduct(environment = environment), action = action)

    @AdminSecured
    suspend fun deleteTechnology(input: UUID, environment: DataFetchingEnvironment): Boolean =
        service.toDelete(input = input, token = deduct(environment = environment))

}