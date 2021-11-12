package com.rsba.component_microservice.resolver.mutation

import  com.rsba.component_microservice.domain.input.*
import com.rsba.component_microservice.domain.model.Item
import  com.rsba.component_microservice.aspect.AdminSecured
import com.rsba.component_microservice.domain.model.MutationAction
import com.rsba.component_microservice.domain.repository.ItemRepository
import com.rsba.component_microservice.domain.security.TokenAnalyzer
import graphql.kickstart.tools.GraphQLMutationResolver
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import java.util.*

@Component
class ItemMutation(private val service: ItemRepository, private val deduct: TokenAnalyzer) : GraphQLMutationResolver {

    @AdminSecured
    suspend fun createOrEditItem(
        input: ItemInput,
        action: MutationAction? = null,
        environment: DataFetchingEnvironment
    ): Optional<Item> =
        service.toCreateOrEdit(input = input, token = deduct(environment = environment), action = action)

    @AdminSecured
    suspend fun deleteItem(input: UUID, environment: DataFetchingEnvironment): Boolean =
        service.toDelete(input = input, token = deduct(environment = environment))
}