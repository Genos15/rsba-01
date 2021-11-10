package com.rsba.component_microservice.data.service.implementations

import com.rsba.component_microservice.domain.input.ItemCategoryInput
import com.rsba.component_microservice.domain.model.Item
import com.rsba.component_microservice.domain.model.ItemCategory
import com.rsba.component_microservice.domain.repository.ItemCategoryRepository
import com.rsba.component_microservice.domain.usecase.common.*
import com.rsba.component_microservice.domain.usecase.custom.item_category.RetrieveItemCategoryChildrenDataLoaderUseCase
import com.rsba.component_microservice.domain.usecase.custom.item_category.RetrieveItemCategoryChildrenUseCase
import com.rsba.component_microservice.domain.usecase.custom.item_category.RetrieveItemCategorySubItemsDataLoaderUseCase
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import java.util.*

@Service
class ItemCategoryService(
    private val database: DatabaseClient,
    @Qualifier("create_edit_item_category") private val createOrEditUseCase: CreateOrEditUseCase<ItemCategoryInput, ItemCategory>,
    @Qualifier("delete_item_category") private val deleteUseCase: DeleteUseCase<ItemCategory>,
    @Qualifier("retrieve_item_category") private val retrieveUseCase: RetrieveUseCase<ItemCategory>,
    @Qualifier("search_item_category") private val searchUseCase: SearchUseCase<ItemCategory>,
    @Qualifier("find_item_category") private val findUseCase: FindUseCase<ItemCategory>,
    @Qualifier("count_item_category") private val countUseCase: CountUseCase,
    private val retrieveChildren: RetrieveItemCategoryChildrenUseCase,
    private val retrieveChildrenDataLoader: RetrieveItemCategoryChildrenDataLoaderUseCase,
    private val myItems: RetrieveItemCategorySubItemsDataLoaderUseCase
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

    override suspend fun count(token: UUID): Int = countUseCase(database = database, token = token)

    override suspend fun items(ids: Set<UUID>, first: Int, after: UUID?, token: UUID): Map<UUID, List<Item>> =
        myItems(ids = ids, database = database, first = first, after = after, token = token)
}