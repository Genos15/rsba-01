package com.rsba.component_microservice.resolver.query

import com.rsba.component_microservice.domain.model.Item
import com.rsba.component_microservice.aspect.AdminSecured
import com.rsba.component_microservice.domain.model.InformationUsage
import com.rsba.component_microservice.domain.model.Operation
import com.rsba.component_microservice.domain.repository.ItemRepository
import com.rsba.component_microservice.domain.security.TokenAnalyzer
import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.relay.*
import org.springframework.stereotype.Component
import java.util.*

import graphql.schema.DataFetchingEnvironment
import java.time.OffsetDateTime


@Component
class ItemQueryResolver(val service: ItemRepository, private val deduct: TokenAnalyzer) : GraphQLQueryResolver,
    GenericRetrieveConnection {

    @AdminSecured
    suspend fun retrieveItems(
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<Item> = perform(
        entries = service.retrieve(first = first, after = after, token = deduct(environment = environment)),
        first = first,
        after = after,
    )

    @AdminSecured
    suspend fun searchItems(
        input: String,
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<Item> = perform(
        entries = service.search(
            input = input,
            first = first,
            after = after,
            token = deduct(environment = environment)
        ),
        first = first,
        after = after
    )

    suspend fun countItems(environment: DataFetchingEnvironment): Int =
        service.count(token = deduct(environment = environment))

    suspend fun findItem(id: UUID, environment: DataFetchingEnvironment): Optional<Item> =
        service.find(id = id, token = deduct(environment = environment))

    suspend fun retrieveItemOperations(
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

    suspend fun retrieveItemComponents(
        id: UUID,
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<Item> = perform(
        entries = service.components(
            ids = setOf(id),
            token = deduct(environment = environment),
            first = first,
            after = after
        ),
        first = first,
        after = after,
        id = id
    )

    suspend fun retrieveItemUsage(
        first: Int,
        after: UUID? = null,
        from: OffsetDateTime? = null,
        to: OffsetDateTime? = null,
        environment: DataFetchingEnvironment
    ): Connection<InformationUsage> = perform(
        entries = service.usages(
            first = first,
            after = after,
            token = deduct(environment = environment),
            from = from,
            to = to
        ),
        first = first,
        after = after
    )

    suspend fun searchItemUsage(
        input: String,
        first: Int,
        after: UUID? = null,
        from: OffsetDateTime? = null,
        to: OffsetDateTime? = null,
        environment: DataFetchingEnvironment
    ): Connection<InformationUsage> = perform(
        entries = service.usages(
            first = first,
            after = after,
            token = deduct(environment = environment),
            from = from,
            to = to,
            input = input
        ),
        first = first,
        after = after
    )

    suspend fun findItemUsage(
        input: UUID,
        from: OffsetDateTime? = null,
        to: OffsetDateTime? = null,
        environment: DataFetchingEnvironment
    ): Optional<InformationUsage> =
        service.usage(input = input, from = from, to = to, token = deduct(environment = environment))

}