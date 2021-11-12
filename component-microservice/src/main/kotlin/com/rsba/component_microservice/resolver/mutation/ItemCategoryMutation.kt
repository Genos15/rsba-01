package com.rsba.component_microservice.resolver.mutation

import  com.rsba.component_microservice.domain.input.*
import  com.rsba.component_microservice.aspect.AdminSecured
import com.rsba.component_microservice.domain.model.ItemCategory
import com.rsba.component_microservice.domain.model.MutationAction
import com.rsba.component_microservice.domain.repository.ItemCategoryRepository
import com.rsba.component_microservice.domain.security.TokenAnalyzer
import graphql.kickstart.tools.GraphQLMutationResolver
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import java.util.*

@Component
class ItemCategoryMutation(private val service: ItemCategoryRepository, private val deduct: TokenAnalyzer) :
    GraphQLMutationResolver {

    @AdminSecured
    suspend fun createOrEditItemCategory(
        input: ItemCategoryInput,
        action: MutationAction? = null,
        environment: DataFetchingEnvironment
    ): Optional<ItemCategory> = service.createOrEdit(input = input, token = deduct(environment = environment), action = action)

    @AdminSecured
    suspend fun deleteItemCategory(input: UUID, environment: DataFetchingEnvironment): Boolean =
        service.delete(input = input, token = deduct(environment = environment))

}