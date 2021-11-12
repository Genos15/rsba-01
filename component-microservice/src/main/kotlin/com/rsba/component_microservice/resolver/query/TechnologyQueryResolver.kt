package com.rsba.component_microservice.resolver.query

import com.rsba.component_microservice.aspect.AdminSecured
import com.rsba.component_microservice.domain.model.Operation
import com.rsba.component_microservice.domain.model.Technology
import com.rsba.component_microservice.domain.repository.TechnologyRepository
import com.rsba.component_microservice.domain.security.TokenAnalyzer
import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.relay.Connection
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import java.util.*

@Component
class TechnologyQueryResolver(val service: TechnologyRepository, private val deduct: TokenAnalyzer) :
    GraphQLQueryResolver, GenericRetrieveConnection {

    @AdminSecured
    suspend fun retrieveTechnologies(
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<Technology> = perform(
        entries = service.retrieve(
            first = first,
            after = after,
            token = deduct(environment = environment)
        ),
        first = first,
        after = after
    )

    @AdminSecured
    suspend fun searchTechnologies(
        input: String,
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<Technology> = perform(
        entries = service.search(
            input = input,
            first = first,
            after = after,
            token = deduct(environment = environment)
        ),
        first = first,
        after = after
    )

    suspend fun findTechnology(id: UUID, environment: DataFetchingEnvironment): Optional<Technology> =
        service.find(id = id, token = deduct(environment = environment))

    suspend fun retrieveTechnologyOperations(
        id: UUID,
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<Operation> = perform(
        entries = service.operations(
            ids = setOf(id),
            token = deduct(environment = environment),
            first = first,
            after = after
        ),
        first = first,
        after = after,
        id = id
    )

    suspend fun countTechnologies(environment: DataFetchingEnvironment): Int =
        service.count(token = deduct(environment = environment))
}