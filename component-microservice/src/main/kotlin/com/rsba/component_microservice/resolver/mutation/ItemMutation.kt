package com.rsba.component_microservice.resolver.mutation

import  com.rsba.component_microservice.domain.input.*
import com.rsba.component_microservice.domain.model.Item
import  com.rsba.component_microservice.aspect.AdminSecured
import com.rsba.component_microservice.domain.repository.ItemRepository
import com.rsba.component_microservice.domain.security.TokenAnalyzer
import graphql.kickstart.tools.GraphQLMutationResolver
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import java.util.*

@Component
class ItemMutation(private val service: ItemRepository, private val deduct: TokenAnalyzer) : GraphQLMutationResolver {

    @AdminSecured
    suspend fun createOrEditItem(input: ItemInput, environment: DataFetchingEnvironment): Optional<Item> =
        service.toCreateOrEdit(input = input, token = deduct(environment = environment))

    @AdminSecured
    suspend fun deleteItem(input: UUID, environment: DataFetchingEnvironment): Boolean =
        service.toDelete(input = input, token = deduct(environment = environment))

    @AdminSecured
    suspend fun attachOperationToItem(input: ItemInput, environment: DataFetchingEnvironment): Optional<Item> =
        service.toAttachOperation(input = input, token = deduct(environment = environment))


    @AdminSecured
    suspend fun attachSubItemToItem(input: ItemInput, environment: DataFetchingEnvironment): Optional<Item> =
        service.toAttachSubItem(input = input, token = deduct(environment = environment))


    @AdminSecured
    suspend fun detachSubItemToItem(input: ItemInput, environment: DataFetchingEnvironment): Optional<Item> =
        service.toDetachSubItem(input = input, token = deduct(environment = environment))

    @AdminSecured
    suspend fun detachOperationToItem(input: ItemInput, environment: DataFetchingEnvironment): Optional<Item> =
        service.toDetachOperation(input = input, token = deduct(environment = environment))

    @AdminSecured
    suspend fun attachTechnologyWithItem(
        input: ItemTechnologyInput,
        environment: DataFetchingEnvironment
    ): Optional<Item> = service.toAttachTechnology(input = input, token = deduct(environment = environment))

    suspend fun importItemFromJsonFile(environment: DataFetchingEnvironment): Optional<Boolean> =
        service.toImportItemFromJsonFile(environment = environment)
}