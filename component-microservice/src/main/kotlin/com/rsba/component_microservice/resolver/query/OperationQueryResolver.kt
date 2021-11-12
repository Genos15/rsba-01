package com.rsba.component_microservice.resolver.query

import com.rsba.component_microservice.domain.model.Operation
import com.rsba.component_microservice.aspect.AdminSecured
import com.rsba.component_microservice.domain.model.Group
import com.rsba.component_microservice.domain.repository.OperationRepository
import com.rsba.component_microservice.domain.security.TokenAnalyzer
import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.relay.*
import org.springframework.stereotype.Component
import java.util.*

import graphql.schema.DataFetchingEnvironment


@Component
class OperationQueryResolver(val service: OperationRepository, private val deduct: TokenAnalyzer) :
    GraphQLQueryResolver, GenericRetrieveConnection {

    @AdminSecured
    suspend fun retrieveOperations(
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<Operation> = perform(
        entries = service.retrieve(
            first = first,
            after = after,
            token = deduct(environment = environment)
        ),
        first = first,
        after = after
    )

    @AdminSecured
    suspend fun searchOperations(
        input: String,
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<Operation> = perform(
        entries = service.search(
            input = input,
            first = first,
            after = after,
            token = deduct(environment = environment)
        ),
        first = first,
        after = after
    )

    suspend fun findOperation(id: UUID, environment: DataFetchingEnvironment): Optional<Operation> =
        service.find(id = id, token = deduct(environment = environment))

    suspend fun retrieveOperationDepartments(
        id: UUID,
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<Group> = perform(
        entries = service.departments(
            ids = setOf(id),
            token = deduct(environment = environment),
            first = first,
            after = after
        ),
        first = first,
        after = after,
        id = id
    )

    suspend fun countOperations(environment: DataFetchingEnvironment): Int =
        service.count(token = deduct(environment = environment))
}