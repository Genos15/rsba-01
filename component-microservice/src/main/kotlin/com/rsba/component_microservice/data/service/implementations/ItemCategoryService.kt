package com.rsba.component_microservice.data.service.implementations

import com.rsba.component_microservice.domain.input.ItemCategoryInput
import com.rsba.component_microservice.domain.model.ItemCategory
import com.rsba.component_microservice.domain.repository.ItemCategoryRepository
import com.rsba.component_microservice.domain.usecase.common.*
import com.rsba.component_microservice.domain.usecase.custom.item_category.RetrieveItemCategoryChildrenDataLoaderUseCase
import com.rsba.component_microservice.domain.usecase.custom.item_category.RetrieveItemCategoryChildrenUseCase
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import java.util.*

@Service
class ItemCategoryService(
    private val database: DatabaseClient,
    private val createOrEditUseCase: CreateOrEditUseCase<ItemCategoryInput, ItemCategory>,
    private val deleteUseCase: DeleteUseCase<ItemCategory>,
    private val retrieveUseCase: RetrieveUseCase<ItemCategory>,
    private val searchUseCase: SearchUseCase<ItemCategory>,
    private val findUseCase: FindUseCase<ItemCategory>,
    private val retrieveChildren: RetrieveItemCategoryChildrenUseCase,
    private val retrieveChildrenDataLoader: RetrieveItemCategoryChildrenDataLoaderUseCase,
) : ItemCategoryRepository {

    override suspend fun createOrEdit(input: ItemCategoryInput, token: UUID): Optional<ItemCategory> =
        createOrEditUseCase(database = database, input = input, token = token)

    override suspend fun delete(input: UUID, token: UUID): Boolean =
        deleteUseCase(database = database, input = input, token = token)

    override suspend fun find(id: UUID, token: UUID): Optional<ItemCategory> =
        findUseCase(database = database, id = id, token = token)

    override suspend fun retrieve(first: Int, after: UUID?, token: UUID): List<ItemCategory> =
        retrieveUseCase(database = database, first = first, after = after, token = token)

    override suspend fun search(input: String, first: Int, after: UUID?, token: UUID): List<ItemCategory> =
        searchUseCase(database = database, first = first, after = after, token = token, input = input)

    override suspend fun children(id: UUID, first: Int, after: UUID?, token: UUID): List<ItemCategory> =
        retrieveChildren(database = database, id = id, first = first, after = after, token = token)

    override suspend fun children(ids: Set<UUID>): Map<UUID, List<ItemCategory>> =
        retrieveChildrenDataLoader(database = database, ids = ids, token = UUID.randomUUID())
}