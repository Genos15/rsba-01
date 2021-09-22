package com.rsba.component_microservice.service

import com.rsba.component_microservice.context.CustomGraphQLContext
import com.rsba.component_microservice.domain.input.CreateOrEditCategoryOfItemInput
import com.rsba.component_microservice.domain.input.CreateOrEditItemInput
import com.rsba.component_microservice.domain.input.ItemAndOperationInput
import com.rsba.component_microservice.domain.input.ItemTechnologyInput
import com.rsba.component_microservice.domain.model.*
import com.rsba.component_microservice.query.database.*
import com.rsba.component_microservice.repository.ItemRepository
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.reactive.awaitFirstOrElse
import mu.KLogger
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import reactor.kotlin.core.publisher.toMono
import java.io.File
import java.io.FileReader
import java.util.*
import java.util.stream.Collectors

@Service
class ItemService(
    private val database: DatabaseClient,
    private val queryHelper: ItemDBQuery,
    private val logger: KLogger
) : ItemRepository {

    override suspend fun createOrEditItem(input: CreateOrEditItemInput, token: UUID): Optional<Item> =
        database.sql(queryHelper.onCreateOrEditItem(input = input, token = token))
            .map { row -> ItemDBHandler2.one(row = row) }
            .first()
            .flatMap { optional ->
                if (optional.isPresent && (input.operations?.size ?: 0) > 0 && input.id == null) {
                    return@flatMap Flux.fromIterable(input.operations!!)
                        .flatMap {
                            val payload =
                                ItemAndOperationInput(operationId = UUID.fromString(it), itemId = optional.get().id)
                            database.sql(queryHelper.onAttachOperationWithItem(input = payload, token = token))
                                .map { row -> ItemDBHandler2.one(row = row) }
                                .first()
                        }.map { optional }
                        .toMono()
                } else {
                    return@flatMap Mono.just(optional)
                }
            }
            .awaitFirstOrElse { Optional.empty() }

    override suspend fun createOrEditCategoryOfItem(
        input: CreateOrEditCategoryOfItemInput,
        token: UUID
    ): Optional<CategoryOfItem> =
        database.sql(queryHelper.onCreateOrEditCategoryOfItemQuery(input = input, token = token))
            .map { row -> CategoryOfItemDBHandler.one(row = row) }
            .first()
            .awaitFirstOrElse { Optional.empty() }

    override suspend fun deleteCategoryOfItem(input: UUID, token: UUID): Int =
        database.sql(queryHelper.onDeleteCategoryOfItemQuery(input = input, token = token))
            .map { row, meta -> CategoryOfItemDBHandler.count(row = row, meta = meta) }
            .first()
            .awaitFirstOrElse { 0 }

    override suspend fun deleteItem(input: UUID, token: UUID): Int =
        database.sql(queryHelper.onDeleteItem(input = input, token = token))
            .map { row, meta -> ItemDBHandler2.count(row = row, meta = meta) }
            .first()
            .awaitFirstOrElse { 0 }

    override suspend fun onRetrieveItem(first: Int, after: UUID?, token: UUID): List<Item> =
        database.sql(queryHelper.onRetrieveAllItem(first = first, after = after, token = token))
            .map { row -> ItemDBHandler2.all(row = row) }
            .first()
            .awaitFirstOrElse { emptyList() }

    override suspend fun onRetrieveItemHavingCategory(first: Int, after: UUID?, token: UUID): List<Item> =
        database.sql(queryHelper.onRetrieveAllItemHavingCategory(first = first, after = after, token = token))
            .map { row -> ItemDBHandler2.all(row = row) }
            .first()
            .awaitFirstOrElse { emptyList() }

    override suspend fun onRetrieveCategoryOfItem(first: Int, after: UUID?, token: UUID): List<CategoryOfItem> =
        database.sql(queryHelper.onRetrieveAllCategoryOfItemQuery(first = first, after = after, token = token))
            .map { row -> CategoryOfItemDBHandler.all(row = row) }
            .first()
            .awaitFirstOrElse { emptyList() }

    override suspend fun attachOperationToItem(input: ItemAndOperationInput, token: UUID): Optional<Item> =
        database.sql(queryHelper.onAttachOperationWithItem(input = input, token = token))
            .map { row -> ItemDBHandler2.one(row = row) }
            .first()
            .awaitFirstOrElse { Optional.empty() }

    override suspend fun detachOperationToItem(input: ItemAndOperationInput, token: UUID): Optional<Item> =
        database.sql(queryHelper.onDetachOperationWithItem(input = input, token = token))
            .map { row -> ItemDBHandler2.one(row = row) }
            .first()
            .awaitFirstOrElse { Optional.empty() }

    override suspend fun retrieveOperationInItem(
        ids: Set<UUID>,
        userId: UUID,
        page: Int,
        size: Int
    ): Map<UUID, MutableList<Operation>> = Flux.fromIterable(ids)
        .flatMap { id ->
            return@flatMap database.sql(queryHelper.onRetrieveOperationsByItemId(itemId = id))
                .map { row -> OperationDBHandler2.all(row = row) }
                .first()
                .map { AbstractMap.SimpleEntry(id, it.toMutableList()) }
        }
        .collect(Collectors.toList())
        .map {
            val map = mutableMapOf<UUID, MutableList<Operation>>()
            it.forEach { element -> map[element.key] = element.value }
            return@map map.toMap()
        }
        .awaitFirstOrElse { emptyMap() }

    override suspend fun retrieveCategoryInItem(
        ids: Set<UUID>,
        userId: UUID,
        page: Int,
        size: Int
    ): Map<UUID, CategoryOfItem?> = Flux.fromIterable(ids)
        .flatMap { id ->
            return@flatMap database.sql(queryHelper.onRetrieveCategoryByItemId(itemId = id))
                .map { row -> CategoryOfItemDBHandler.one(row = row) }
                .first()
                .map { AbstractMap.SimpleEntry(id, it) }
        }
        .collect(Collectors.toList())
        .map {
            val map = mutableMapOf<UUID, CategoryOfItem?>()
            it.forEach { element -> map[element.key] = element.value.orElse(null) }
            return@map map.toMap()
        }
        .awaitFirstOrElse { emptyMap() }

    override suspend fun attachTechnology(input: ItemTechnologyInput, token: UUID): Optional<Item> =
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
                logger.warn { "+ItemService->attachTechnology->error = ${it.message}" }
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

    override suspend fun importItemFromJsonFile(environment: DataFetchingEnvironment): Optional<Boolean> =
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

}