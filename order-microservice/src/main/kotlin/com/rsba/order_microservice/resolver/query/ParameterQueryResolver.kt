package com.rsba.order_microservice.resolver.query


import com.rsba.order_microservice.aspect.AdminSecured
import com.rsba.order_microservice.configuration.request_helper.CursorUtil
import com.rsba.order_microservice.data.context.token.ITokenImpl
import com.rsba.order_microservice.domain.repository.ParameterRepository
import com.rsba.order_microservice.domain.security.TokenAnalyzer
import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.relay.*
import org.springframework.stereotype.Component
import java.util.*

import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import com.rsba.order_microservice.domain.model.Parameter


@Component
class ParameterQueryResolver(
    val service: ParameterRepository,
    private val deduct: TokenAnalyzer,
    val logger: KLogger
) : GraphQLQueryResolver, ITokenImpl, GenericRetrieveConnection {

    @AdminSecured
    suspend fun retrieveAllParameters(
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<Parameter>? = perform(
        entries = service.retrieve(
            first = first,
            after = after,
            token = deduct(environment = environment)
        ),
        first = first,
        after = after
    )

    @AdminSecured
    suspend fun searchParameters(
        input: String,
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<Parameter>? = perform(
        entries = service.search(
            input = input,
            first = first,
            after = after,
            token = deduct(environment = environment)
        ),
        first = first,
        after = after
    )

    @AdminSecured
    suspend fun retrieveParametersByTaskId(taskId: UUID, environment: DataFetchingEnvironment): List<Parameter> =
        service.retrieveByTaskId(taskId = taskId, token = readToken(environment = environment))

    @AdminSecured
    suspend fun retrieveParametersByItemId(itemId: UUID, environment: DataFetchingEnvironment): List<Parameter> =
        service.retrieveByItemId(itemId = itemId, token = readToken(environment = environment))

    @AdminSecured
    suspend fun retrieveParameterById(id: UUID, environment: DataFetchingEnvironment): Optional<Parameter> =
        service.retrieveById(id = id, token = readToken(environment = environment))
}