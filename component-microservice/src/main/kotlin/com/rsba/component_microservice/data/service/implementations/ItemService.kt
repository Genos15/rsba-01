package com.rsba.component_microservice.data.service.implementations


import com.rsba.component_microservice.domain.input.ItemInput
import com.rsba.component_microservice.domain.model.*
import com.rsba.component_microservice.domain.repository.ItemRepository
import com.rsba.component_microservice.domain.usecase.common.*
import com.rsba.component_microservice.domain.usecase.custom.item.*
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.*

@Service
class ItemService(
    private val database: DatabaseClient,
    @Qualifier("create_edit_item") private val createOrEditUseCase: CreateOrEditUseCase<ItemInput, Item>,
    @Qualifier("delete_item") private val deleteUseCase: DeleteUseCase<Item>,
    @Qualifier("find_item") private val findUseCase: FindUseCase<Item>,
    @Qualifier("retrieve_item") private val retrieveUseCase: RetrieveUseCase<Item>,
    @Qualifier("search_item") private val searchUseCase: SearchUseCase<Item>,
    @Qualifier("count_items") private val countUseCase: CountUseCase,
    private val categoryUseCase: RetrieveCategoryDataLoaderUseCase,
    private val operationsUseCase: RetrieveOperationDataLoaderUseCase,
    private val componentsUseCase: RetrieveSubItemDataLoaderUseCase,
    @Qualifier("find_item_information_usage") private val findInformationUsageUseCase: FindInformationUsageUseCase,
    @Qualifier("retrieve_item_information_usage") private val retrieveInformationUsageUseCase: RetrieveInformationUsageUseCase,
    @Qualifier("search_item_information_usage") private val searchInformationUsageUseCase: SearchInformationUsageUseCase
) : ItemRepository {

    override suspend fun toCreateOrEdit(input: ItemInput, action: MutationAction?, token: UUID): Optional<Item> =
        createOrEditUseCase(database = database, input = input, token = token, action = action)

    override suspend fun toDelete(input: UUID, token: UUID): Boolean =
        deleteUseCase(database = database, input = input, token = token)

    override suspend fun find(id: UUID, token: UUID): Optional<Item> =
        findUseCase(database = database, id = id, token = token)

    override suspend fun retrieve(first: Int, after: UUID?, token: UUID): List<Item> =
        retrieveUseCase(database = database, first = first, after = after, token = token)

    override suspend fun operations(ids: Set<UUID>, first: Int, after: UUID?, token: UUID): Map<UUID, List<Operation>> =
        operationsUseCase(database = database, ids = ids, token = token, first = first, after = after)

    override suspend fun components(ids: Set<UUID>, first: Int, after: UUID?, token: UUID): Map<UUID, List<Item>> =
        componentsUseCase(database = database, ids = ids, token = token, first = first, after = after)

    override suspend fun category(ids: Set<UUID>): Map<UUID, Optional<ItemCategory>> =
        categoryUseCase(database = database, ids = ids, token = UUID.randomUUID())

    override suspend fun count(token: UUID): Int = countUseCase(database = database, token = token)

    override suspend fun usages(
        first: Int,
        after: UUID?,
        from: OffsetDateTime?,
        to: OffsetDateTime?,
        token: UUID
    ): List<InformationUsage> =
        retrieveInformationUsageUseCase(
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
        searchInformationUsageUseCase(
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
        findInformationUsageUseCase(from = from, to = to, token = token, input = input)

    override suspend fun search(input: String, first: Int, after: UUID?, token: UUID): List<Item> =
        searchUseCase(database = database, first = first, after = after, token = token, input = input)

}