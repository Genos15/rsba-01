package com.rsba.component_microservice.data.service.implementations

import com.rsba.component_microservice.data.context.CustomGraphQLContext
import com.rsba.component_microservice.database.ItemDBHandler2
import com.rsba.component_microservice.database.ItemQueries
import com.rsba.component_microservice.domain.input.ItemInput
import com.rsba.component_microservice.domain.input.ItemTechnologyInput
import com.rsba.component_microservice.domain.model.*
import com.rsba.component_microservice.query.database.*
import com.rsba.component_microservice.domain.repository.ItemRepository
import com.rsba.component_microservice.domain.usecase.common.*
import com.rsba.component_microservice.domain.usecase.custom.item.*
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.reactive.awaitFirstOrElse
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.io.File
import java.io.FileReader
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
    private val attachOperationUseCase: AttachOperationToItemUseCase,
    private val attachSubItemUseCase: AttachSubItemToItemUseCase,
    private val detachSubItemUseCase: DetachSubItemToItemUseCase,
    private val detachOperationUseCase: DetachOperationToItemUseCase,
    private val myCategoryUseCase: RetrieveCategoryDataLoaderUseCase,
    private val myOperationsUseCase: RetrieveOperationDataLoaderUseCase,
    private val mySubItemsUseCase: RetrieveSubItemDataLoaderUseCase,
) : ItemRepository {

    override suspend fun toCreateOrEdit(input: ItemInput, token: UUID): Optional<Item> =
        createOrEditUseCase(database = database, input = input, token = token)

    override suspend fun toDelete(input: UUID, token: UUID): Boolean =
        deleteUseCase(database = database, input = input, token = token)

    override suspend fun find(id: UUID, token: UUID): Optional<Item> =
        findUseCase(database = database, id = id, token = token)

    override suspend fun retrieve(first: Int, after: UUID?, token: UUID): List<Item> =
        retrieveUseCase(database = database, first = first, after = after, token = token)

    override suspend fun toAttachOperation(input: ItemInput, token: UUID): Optional<Item> =
        attachOperationUseCase(database = database, input = input, token = token)

    override suspend fun toAttachSubItem(input: ItemInput, token: UUID): Optional<Item> =
        attachSubItemUseCase(database = database, input = input, token = token)

    override suspend fun toDetachOperation(input: ItemInput, token: UUID): Optional<Item> =
        detachOperationUseCase(database = database, input = input, token = token)

    override suspend fun toDetachSubItem(input: ItemInput, token: UUID): Optional<Item> =
        detachSubItemUseCase(database = database, input = input, token = token)

    override suspend fun operations(ids: Set<UUID>, first: Int, after: UUID?, token: UUID): Map<UUID, List<Operation>> =
        myOperationsUseCase(database = database, ids = ids, token = token, first = first, after = after)

    override suspend fun components(ids: Set<UUID>, first: Int, after: UUID?, token: UUID): Map<UUID, List<Item>> =
        mySubItemsUseCase(database = database, ids = ids, token = token, first = first, after = after)

    override suspend fun category(ids: Set<UUID>): Map<UUID, Optional<ItemCategory>> =
        myCategoryUseCase(database = database, ids = ids, token = UUID.randomUUID())

    override suspend fun toAttachTechnology(input: ItemTechnologyInput, token: UUID): Optional<Item> =
        Flux.fromIterable(input.technologiesIds ?: emptyList())
            .parallel()
            .map { ItemTechnology(technologyId = UUID.fromString(it), itemId = input.itemId) }
            .flatMap {
                database.sql(ItemQueries.importTechnology(input = it, token = token))
                    .map { row -> ItemDBHandler2.one(row = row) }
                    .first()
            }
            .runOn(Schedulers.parallel())
            .sequential()
            .collectList()
            .map { it.firstOrNull() ?: Optional.empty() }
            .onErrorResume {
                throw it
            }
            .awaitFirstOrElse { Optional.empty() }

    override suspend fun itemsByCategoryId(categoryId: UUID, first: Int, after: UUID?, token: UUID): List<Item> =
        database.sql(
            ItemQueries.itemsByCategoryId(
                first = first,
                after = after,
                token = token,
                categoryId = categoryId
            )
        ).map { row -> ItemDBHandler2.all(row = row) }
            .first()
            .awaitFirstOrElse { emptyList() }

    override suspend fun toImportItemFromJsonFile(environment: DataFetchingEnvironment): Optional<Boolean> =
        Flux.just(environment)
            .map {
                val context: CustomGraphQLContext = it.getContext()
                if (context.fileParts.isEmpty() || context.fileParts.firstOrNull() == null) {
                    throw RuntimeException("FILE NOT FOUND")
                }
                val part = context.fileParts.first()
                val file = File("filename")
                file.writeBytes(part.inputStream.readBytes())
                val reader = FileReader(file)
                val jsonParser = JSONParser()
                val obj: Any = jsonParser.parse(reader)
                val list: List<ItemFromOld> = (obj as JSONArray).map { emp ->
                    ItemFromOld(
                        name = (emp as JSONObject)["name"] as String,
                        description = emp["description"] as String?
                    )
                }
                Flux.fromIterable(list)
            }
            .parallel()
            .flatMap {
                it.flatMap { elt ->
                    database.sql(ItemQueries.addOldItem(input = elt, token = UUID.randomUUID()))
                        .map { row -> row }
                        .first()
                }
            }
            .runOn(Schedulers.parallel())
            .map {
                Optional.of(true)
            }.awaitFirstOrElse { Optional.of(false) }

    override suspend fun count(token: UUID): Int = countUseCase(database = database, token = token)

    override suspend fun search(input: String, first: Int, after: UUID?, token: UUID): List<Item> =
        searchUseCase(database = database, first = first, after = after, token = token, input = input)

}