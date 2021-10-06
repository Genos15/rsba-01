package  com.rsba.order_microservice.service

import com.rsba.order_microservice.database.*
import com.rsba.order_microservice.domain.input.ItemAndItemInput
import com.rsba.order_microservice.domain.model.*
import com.rsba.order_microservice.repository.ItemRepository
import com.rsba.order_microservice.service.implementation.items.EditComponentAndItemImpl
import com.rsba.order_microservice.service.implementation.items.RetrieveComponentAndItemImpl
import kotlinx.coroutines.reactive.awaitFirstOrElse
import mu.KLogger
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.util.*

@Service
class ItemService(private val logger: KLogger, private val database: DatabaseClient) : ItemRepository,
    EditComponentAndItemImpl, RetrieveComponentAndItemImpl {

    override suspend fun myOperations(
        ids: Set<UUID>,
        userId: UUID,
        page: Int,
        size: Int
    ): Map<UUID, List<Operation>> = Flux.fromIterable(ids)
        .parallel()
        .flatMap { id ->
            return@flatMap database.sql(ItemDBQueries.retrieveMyOperations(itemId = id))
                .map { row, meta -> OperationDBHandler.all(row = row, meta = meta) }
                .first()
                .map { AbstractMap.SimpleEntry(id, it) }
        }
        .runOn(Schedulers.parallel())
        .sequential()
        .collectList()
        .map {
            val map = mutableMapOf<UUID, List<Operation>>()
            it.filterNotNull().forEach { element -> map[element.key] = element.value }
            return@map map.toMap()
        }
        .onErrorResume {
            logger.warn { "+ItemService -> myOperations -> error = ${it.message}" }
            throw it
        }
        .awaitFirstOrElse { emptyMap() }

    override suspend fun myCategory(ids: Set<Item>, userId: UUID, page: Int, size: Int): Map<Item, CategoryOfItem?> =
        Flux.fromIterable(ids)
            .parallel()
            .flatMap { id ->
                return@flatMap database.sql(ItemDBQueries.retrieveMyCategory(input = id))
                    .map { row -> CategoryDBHandler.one(row = row) }
                    .first()
                    .map { AbstractMap.SimpleEntry(id, it) }
            }
            .runOn(Schedulers.parallel())
            .sequential()
            .collectList()
            .map {
                val map = mutableMapOf<Item, CategoryOfItem?>()
                it.forEach { element -> map[element.key] = element.value.orElse(null) }
                return@map map.toMap()
            }
            .onErrorResume {
                logger.warn { "+ItemService->myOperations->error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { emptyMap() }

    override suspend fun myTasks(ids: Set<Item>, userId: UUID, page: Int, size: Int): Map<Item, List<Task>> =
        Flux.fromIterable(ids)
            .parallel()
            .flatMap { id ->
                return@flatMap database.sql(ItemDBQueries.retrieveMyTasks(instance = id))
                    .map { row, meta -> TaskDBHandler.all(row = row, meta = meta) }
                    .first()
                    .map { AbstractMap.SimpleEntry(id, it) }
            }
            .runOn(Schedulers.parallel())
            .sequential()
            .collectList()
            .map {
                val map = mutableMapOf<Item, List<Task>>()
                it.filterNotNull().forEach { element -> map[element.key] = element.value }
                return@map map.toMap()
            }
            .onErrorResume {
                logger.warn { "+ItemService -> myTasks -> error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { emptyMap() }

    override suspend fun myDetails(orderId: UUID, itemId: UUID, token: UUID): Optional<DetailItemInOrder> =
        database.sql(ItemDBQueries.myDetails(orderId = orderId, itemId = itemId, token = token))
            .map { row, meta -> DetailItemInOrderDBHandler.one(row = row, meta = meta) }
            .first()
            .onErrorResume {
                logger.warn { "+ItemService->myDetails->error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { Optional.empty() }

    override suspend fun myDetailActor(
        ids: Set<DetailItemInOrder>,
        userId: UUID,
        page: Int,
        size: Int
    ): Map<DetailItemInOrder, Optional<User>> =
        Flux.fromIterable(ids)
            .parallel()
            .flatMap { id ->
                return@flatMap database.sql(
                    ItemDBQueries.myDetailActor(
                        input = OrderAndItem(
                            orderId = id.orderId,
                            itemId = id.itemId
                        )
                    )
                ).map { row -> UserDBHandler.one(row = row) }
                    .first()
                    .map { AbstractMap.SimpleEntry(id, it) }
            }
            .runOn(Schedulers.parallel())
            .sequential()
            .collectList()
            .map {
                val map = mutableMapOf<DetailItemInOrder, Optional<User>>()
                it.forEach { element -> map[element.key] = element.value ?: Optional.empty() }
                return@map map.toMap()
            }
            .onErrorResume {
                logger.warn { "+TechnologyService->myOperations->error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { emptyMap() }

    override suspend fun myDetailTechnologies(
        ids: Set<DetailItemInOrder>,
        userId: UUID,
        page: Int,
        size: Int
    ): Map<DetailItemInOrder, List<Technology>> =
        Flux.fromIterable(ids)
            .parallel()
            .flatMap { id ->
                return@flatMap database.sql(
                    ItemDBQueries.myDetailTechnologies(
                        input = OrderAndItem(
                            orderId = id.orderId,
                            itemId = id.itemId
                        )
                    )
                ).map { row -> TechnologyDBHandler.all(row = row) }
                    .first()
                    .map { AbstractMap.SimpleEntry(id, it) }
            }
            .runOn(Schedulers.parallel())
            .sequential()
            .collectList()
            .map {
                val map = mutableMapOf<DetailItemInOrder, List<Technology>>()
                it.forEach { element -> map[element.key] = element.value ?: emptyList() }
                return@map map.toMap()
            }
            .onErrorResume {
                logger.warn { "+TechnologyService->myOperations->error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { emptyMap() }

    override suspend fun addOrEditComponentInItem(input: ItemAndItemInput, token: UUID): Optional<Item> =
        addItemAndItemImplFn(input = input, token = token, database = database)

    override suspend fun removeComponentInItem(input: ItemAndItemInput, token: UUID): Optional<Item> =
        removeItemAndItemImplFn(input = input, token = token, database = database)

    override suspend fun myItems(ids: Set<Item>, userId: UUID): Map<Item, List<Item>> =
        retrieveComponentInItemsFn(items = ids, token = UUID.randomUUID(), database = database)

}