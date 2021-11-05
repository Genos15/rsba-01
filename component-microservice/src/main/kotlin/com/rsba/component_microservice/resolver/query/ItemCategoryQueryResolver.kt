package com.rsba.component_microservice.resolver.query


import com.rsba.component_microservice.domain.model.ItemCategory
import com.rsba.component_microservice.domain.repository.ItemCategoryRepository
import com.rsba.component_microservice.domain.security.TokenAnalyzer
import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.relay.*
import org.springframework.stereotype.Component
import java.util.*
import graphql.schema.DataFetchingEnvironment

@Component
class ItemCategoryQueryResolver(private val service: ItemCategoryRepository, private val deduct: TokenAnalyzer) :
    GraphQLQueryResolver, GenericRetrieveConnection {

    suspend fun retrieveItemCategory(
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<ItemCategory>? = perform(
        entry = service.retrieve(
            first = first,
            after = after,
            token = deduct(environment = environment)
        ),
        first = first,
        after = after
    )

    suspend fun searchItemCategory(
        input: String,
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<ItemCategory>? = perform(
        entry = service.search(
            input = input,
            first = first,
            after = after,
            token = deduct(environment = environment)
        ),
        first = first,
        after = after
    )

    suspend fun findItemCategory(id: UUID, environment: DataFetchingEnvironment): Optional<ItemCategory> =
        service.find(id = id, token = deduct(environment = environment))
}