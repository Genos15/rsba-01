package com.rsba.component_microservice.resolver.query


import com.rsba.component_microservice.domain.model.ElkGraph
import com.rsba.component_microservice.domain.model.ElkGraphItemCategoryNode
import com.rsba.component_microservice.domain.model.Item
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
        entries = service.retrieve(
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
        entries = service.search(
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

    suspend fun retrieveItemCategorySubCategories(
        id: UUID,
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<ItemCategory>? = perform(
        entries = service.subCategories(
            ids = setOf(id),
            first = first,
            after = after,
            token = deduct(environment = environment)
        ),
        first = first,
        after = after,
        id = id
    )

    suspend fun countItemCategory(environment: DataFetchingEnvironment): Int =
        service.count(token = deduct(environment = environment))

    suspend fun retrieveItemCategorySubItems(
        id: UUID,
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<Item> = perform(
        entries = service.items(
            ids = setOf(id),
            token = deduct(environment = environment),
            first = first,
            after = after
        ),
        first = first,
        after = after,
        id = id
    )

    suspend fun buildItemCategoryElk(
        from: UUID?,
        height: Int,
        width: Int,
        environment: DataFetchingEnvironment,
    ): ElkGraph<ElkGraphItemCategoryNode> =
        service.elk(token = deduct(environment = environment), from = from, height = height, width = width)

}