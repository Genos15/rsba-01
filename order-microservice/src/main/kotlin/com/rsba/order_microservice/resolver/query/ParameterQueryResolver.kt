package com.rsba.order_microservice.resolver.query


import com.rsba.order_microservice.aspect.AdminSecured
import com.rsba.order_microservice.configuration.request_helper.CursorUtil
import com.rsba.order_microservice.context.token.ITokenImpl
import com.rsba.order_microservice.domain.model.Parameter
import com.rsba.order_microservice.repository.ParameterRepository
import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.relay.*
import org.springframework.stereotype.Component
import java.util.*

import graphql.schema.DataFetchingEnvironment
import mu.KLogger


@Component
class ParameterQueryResolver(
    val cursorUtil: CursorUtil,
    val service: ParameterRepository,
    val logger: KLogger
) : GraphQLQueryResolver, ITokenImpl, GenericRetrieveConnection(myLogger = logger) {

    @AdminSecured
    suspend fun retrieveAllParameters(
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<Parameter>? = retrieveFn(
        entry = service.retrieve(token = readToken(environment = environment), first = first, after = after),
        first = first,
        after = after
    )

    @AdminSecured
    suspend fun searchParameters(
        input: String,
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<Parameter>? = retrieveFn(
        entry = service.search(
            token = readToken(environment = environment),
            first = first,
            after = after,
            input = input
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