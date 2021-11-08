package com.rsba.component_microservice.resolver.mutation

import  com.rsba.component_microservice.domain.input.*
import com.rsba.component_microservice.domain.model.Item
import  com.rsba.component_microservice.aspect.AdminSecured
import com.rsba.component_microservice.data.context.token.TokenImpl
import com.rsba.component_microservice.domain.repository.ItemRepository
import graphql.kickstart.tools.GraphQLMutationResolver
import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import org.springframework.stereotype.Component
import java.util.*

@Component
class ItemMutation(
    private val service: ItemRepository,
    private val logger: KLogger,
    private val tokenImpl: TokenImpl
) : GraphQLMutationResolver {

    @AdminSecured
    suspend fun createOrEditItem(input: ItemInput, environment: DataFetchingEnvironment): Optional<Item> {
        logger.warn { "+ItemMutation -> createOrEditItem" }
        return service.toCreateOrEdit(input = input, token = tokenImpl.read(environment = environment))
    }

    @AdminSecured
    suspend fun deleteItem(input: UUID, environment: DataFetchingEnvironment): Int {
        logger.warn { "+ItemMutation -> deleteItem" }
        return service.toDelete(input = input, token = tokenImpl.read(environment = environment))
    }

    @AdminSecured
    suspend fun attachOperationToItem(
        input: ItemAndOperationInput,
        environment: DataFetchingEnvironment
    ): Optional<Item> {
        logger.warn { "+ItemMutation -> attachOperationToItem" }
        return service.toAttachOperation(input = input, token = tokenImpl.read(environment = environment))
    }

    @AdminSecured
    suspend fun detachOperationToItem(
        input: ItemAndOperationInput,
        environment: DataFetchingEnvironment
    ): Optional<Item> {
        logger.warn { "+ItemMutation -> detachOperationToItem" }
        return service.toDetachOperation(input = input, token = tokenImpl.read(environment = environment))
    }

    @AdminSecured
    suspend fun attachTechnologyWithItem(
        input: ItemTechnologyInput,
        environment: DataFetchingEnvironment
    ): Optional<Item> {
        logger.warn { "+ItemMutation->attachTechnologyWithItem" }
        return service.toAttachTechnology(input = input, token = tokenImpl.read(environment = environment))
    }

    suspend fun importItemFromJsonFile(environment: DataFetchingEnvironment): Optional<Boolean> {
        return service.toImportItemFromJsonFile(environment = environment)
    }
}