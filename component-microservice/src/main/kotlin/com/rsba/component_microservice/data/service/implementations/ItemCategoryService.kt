package com.rsba.component_microservice.data.service.implementations

import com.rsba.component_microservice.domain.input.ItemCategoryInput
import com.rsba.component_microservice.domain.model.*
import com.rsba.component_microservice.domain.repository.ItemCategoryRepository
import com.rsba.component_microservice.domain.usecase.common.*
import com.rsba.component_microservice.domain.usecase.custom.item_category.*
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
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
    private val subCategoriesUseCase: RetrieveItemCategoryChildrenUseCase,
    private val myItemsUseCase: RetrieveItemCategorySubItemsDataLoaderUseCase,
    @Qualifier("item_category_elk") private val elkUseCase: RetrieveFullElkGraphUseCase,
    private val retrieveItemCategoryUsageUseCase: RetrieveItemCategoryUsageUseCase,
    private val findItemCategoryUsageUseCase: FindItemCategoryUsageUseCase,
    private val searchItemCategoryUsageUseCase: SearchItemCategoryUsageUseCase
) : ItemCategoryRepository {

    override suspend fun createOrEdit(
        input: ItemCategoryInput,
        action: MutationAction?,
        token: UUID
    ): Optional<ItemCategory> =
        createOrEditUseCase(database = database, input = input, token = token, action = action)

    override suspend fun delete(input: UUID, token: UUID): Boolean =
        deleteUseCase(database = database, input = input, token = token)

    override suspend fun find(id: UUID, token: UUID): Optional<ItemCategory> =
        findUseCase(database = database, id = id, token = token)

    override suspend fun retrieve(first: Int, after: UUID?, token: UUID): List<ItemCategory> =
        retrieveUseCase(database = database, first = first, after = after, token = token)

    override suspend fun search(input: String, first: Int, after: UUID?, token: UUID): List<ItemCategory> =
        searchUseCase(database = database, first = first, after = after, token = token, input = input)

    override suspend fun subCategories(
        ids: Set<UUID>,
        first: Int,
        after: UUID?,
        token: UUID
    ): Map<UUID, List<ItemCategory>> =
        subCategoriesUseCase(database = database, ids = ids, token = token, first = first, after = after)

    override suspend fun count(token: UUID): Int = countUseCase(database = database, token = token)

    override suspend fun items(ids: Set<UUID>, first: Int, after: UUID?, token: UUID): Map<UUID, List<Item>> =
        myItemsUseCase(ids = ids, database = database, first = first, after = after, token = token)

    override suspend fun elk(
        token: UUID,
        from: UUID?,
        height: Int,
        width: Int,
    ): ElkGraph<ElkGraphItemCategoryNode> =
        elkUseCase(database = database, token = token, from = from, height = height, width = width)

    override suspend fun usages(
        first: Int,
        after: UUID?,
        from: OffsetDateTime?,
        to: OffsetDateTime?,
        token: UUID
    ): List<InformationUsage> =
        retrieveItemCategoryUsageUseCase(
            database = database,
            first = first,
            after = after,
            from = from,
            to = to,
            token = token
        )

    override suspend fun usages(
        input: String,
        first: Int,
        after: UUID?,
        from: OffsetDateTime?,
        to: OffsetDateTime?,
        token: UUID
    ): List<InformationUsage> =
        searchItemCategoryUsageUseCase(
            database = database,
            first = first,
            after = after,
            from = from,
            to = to,
            token = token,
            input = input,
        )

    override suspend fun usage(
        input: UUID,
        from: OffsetDateTime?,
        to: OffsetDateTime?,
        token: UUID
    ): Optional<InformationUsage> =
        findItemCategoryUsageUseCase(database = database, from = from, to = to, token = token, input = input)
}