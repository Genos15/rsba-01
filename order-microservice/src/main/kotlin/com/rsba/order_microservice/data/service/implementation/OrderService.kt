package  com.rsba.order_microservice.data.service.implementation

import com.rsba.order_microservice.data.context.CustomGraphQLContext
import com.rsba.order_microservice.database.*
import com.rsba.order_microservice.domain.input.*
import com.rsba.order_microservice.domain.model.*
import com.rsba.order_microservice.data.publisher.OrderPublisher
import  com.rsba.order_microservice.domain.repository.OrderRepository
import com.rsba.order_microservice.data.service.implementation.orders.OrderDataloaderServiceImpl
import com.rsba.order_microservice.data.service.implementation.orders.ReferenceNumberImpl
import com.rsba.order_microservice.data.service.implementation.orders.RetrieveOrderImpl
import com.rsba.order_microservice.domain.usecase.custom.order.RetrieveOrderCompletionLineGraphUseCase
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
import reactor.core.publisher.SynchronousSink
import java.io.File
import java.io.FileReader
import java.util.*
import java.util.stream.Collectors

@Service
class OrderService(
    private val logger: KLogger,
    private val database: DatabaseClient,
    private val queryHelper: OrderDatabaseQuery,
    private val categoryDataHandler: CategoryDataHandler,
    private val agentDataHandler: AgentDataHandler,
    private val monitorPublisher: OrderPublisher,
    private val completionLineGraphUseCase: RetrieveOrderCompletionLineGraphUseCase
) : OrderRepository, ReferenceNumberImpl, OrderDataloaderServiceImpl, RetrieveOrderImpl {

    override suspend fun createOrder(input: CreateOrderInput, token: UUID): Optional<Order> =
        database.sql(queryHelper.onCreateOrder(input = input, token = token))
            .map { row, meta -> OrderDBHandler.one(row = row, meta = meta) }
            .first()
            .handle { single: Optional<Order>, sink: SynchronousSink<Optional<Order>> ->
                if (single.isPresent) {
                    monitorPublisher.publish(order = OrderForSub(order = single.get()))
                    sink.next(single)
                } else {
                    sink.error(RuntimeException("НЕВОЗМОЖНО СОЗДАТЬ ЗАКАЗ"))
                }
            }
            .onErrorResume {
                logger.warn { "+OrderService -> createOrder -> error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { Optional.empty() }

    override suspend fun deleteOrder(input: UUID, token: UUID): Int =
        database.sql(queryHelper.onDeleteOrder(input = input, token = token))
            .map { row, meta -> OrderDBHandler.count(row = row, meta = meta) }
            .first()
            .handle { single: Int, sink: SynchronousSink<Int> ->
                if (single > 0) {
                    monitorPublisher.publish(order = OrderForSub(id = input, referenceNumber = "", deleted = true))
                    sink.next(single)
                } else {
                    sink.error(RuntimeException("НЕВОЗМОЖНО УДАЛИТЬ ЗАКАЗ"))
                }
            }
            .onErrorResume {
                logger.warn { "+OrderService -> deleteOrder -> error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { 0 }

    override suspend fun onRetrieveAllOrder(first: Int, after: UUID?, token: UUID): MutableList<Order> =
        database.sql(queryHelper.onRetrieveAllOrder(token = token, first = first, after = after))
            .map { row -> OrderDBHandler.all(row = row) }
            .first()
            .map { it.toMutableList() }
            .onErrorResume {
                logger.warn { "+OrderService -> onRetrieveAllOrder -> error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { mutableListOf() }

    override suspend fun orderByUserToken(
        first: Int,
        after: UUID?,
        token: UUID,
        level: OrderLevel?
    ): List<Order> =
        database.sql(
            OrderDBQueries.retrieveOrderByUserToken(
                token = token,
                first = first,
                after = after,
                level = level
            )
        )
            .map { row -> OrderDBHandler.all(row = row) }
            .first()
            .onErrorResume {
                logger.warn { "+OrderService->orderByUserToken->error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { mutableListOf() }

    override suspend fun retrieveManagerOfOrder(
        orderIds: Set<UUID>,
        userId: UUID,
        page: Int,
        size: Int
    ): Map<UUID, Agent?> = Flux.fromIterable(orderIds)
        .flatMap { id ->
            return@flatMap database.sql(queryHelper.onRetrieveManagerOfOrder(input = id))
                .map { row, meta -> agentDataHandler.one(row = row, meta = meta) }
                .first()
                .handle { single: Optional<Agent>, sink: SynchronousSink<Agent> ->
                    if (single.isPresent) {
                        sink.next(single.get())
                    } else {
                        sink.error(RuntimeException("МЕНЕДЖЕР НЕ НАЙДЕН"))
                    }
                }
                .map { AbstractMap.SimpleEntry(id, it) }
                .onErrorResume {
                    logger.warn { "error1 = ${it.message}" }
                    Mono.empty()
                }
        }
        .collect(Collectors.toList())
        .map {
            val map = mutableMapOf<UUID, Agent?>()
            it.forEach { element -> map[element.key] = element.value }
            return@map map.toMap()
        }
        .onErrorResume {
            logger.warn { "+OrderService -> retrieveManagerOfOrder -> error = ${it.message}" }
            throw it
        }
        .awaitFirstOrElse { emptyMap() }

    override suspend fun retrieveAgentOfOrder(
        orderIds: Set<UUID>,
        userId: UUID,
        page: Int,
        size: Int
    ): Map<UUID, Agent?> = Flux.fromIterable(orderIds)
        .flatMap { id ->
            return@flatMap database.sql(queryHelper.onRetrieveAgentOfOrder(input = id))
                .map { row, meta -> agentDataHandler.one(row = row, meta = meta) }
                .first()
                .map { AbstractMap.SimpleEntry(id, it) }
        }
        .collect(Collectors.toList())
        .map {
            val map = mutableMapOf<UUID, Agent?>()
            it.forEach { element -> map[element.key] = element.value.orElse(null) }
            return@map map.toMap()
        }
        .onErrorResume {
            logger.warn { "+OrderService -> retrieveAgentOfOrder -> error = ${it.message}" }
            throw it
        }
        .awaitFirstOrElse { emptyMap() }

    override suspend fun retrieveCategoriesOfOrder(
        orderIds: Set<UUID>,
        userId: UUID,
        page: Int,
        size: Int
    ): Map<UUID, List<CategoryOfItemInOrder>> = mapOf()

    override suspend fun retrieveCategoriesInOrder(
        ids: Set<UUID>,
        userId: UUID,
        page: Int,
        size: Int
    ): Map<UUID, List<CategoryOfItem>> = Flux.fromIterable(ids)
        .flatMap { id ->
            return@flatMap database.sql(queryHelper.onRetrieveCategoriesInOrder(input = id))
                .map { row, meta -> categoryDataHandler.all(row = row, meta = meta) }
                .first()
                .map { AbstractMap.SimpleEntry(id, it) }
        }
        .collect(Collectors.toList())
        .map {
            val map = mutableMapOf<UUID, List<CategoryOfItem>>()
            it.forEach { element -> map[element.key] = element.value ?: emptyList() }
            return@map map.toMap()
        }
        .onErrorResume {
            logger.warn { "+OrderService -> retrieveCategoriesOfOrder -> error = ${it.message}" }
            throw it
        }
        .awaitFirstOrElse { emptyMap() }

    override suspend fun retrieveItemsInOrder(
        ids: Set<UUID>,
        userId: UUID,
        page: Int,
        size: Int
    ): Map<UUID, List<Item>> = Flux.fromIterable(ids)
        .flatMap { id ->
            return@flatMap database.sql(OrderDBQueries.retrieveMyItems(orderId = id))
                .map { row, meta -> ItemDBHandler.all(row = row, meta = meta) }
                .first()
                .map { AbstractMap.SimpleEntry(id, it) }
        }
        .collect(Collectors.toList())
        .map {
            val map = mutableMapOf<UUID, List<Item>>()
            it.forEach { element -> map[element.key] = element.value ?: emptyList() }
            return@map map.toMap()
        }
        .onErrorResume {
            logger.warn { "+ItemService -> myOperations -> error = ${it.message}" }
            throw it
        }
        .awaitFirstOrElse { emptyMap() }

    override fun retrieveCategoriesOfOneOrder(id: UUID): Mono<MutableList<CategoryOfItem>> =
        database.sql(queryHelper.onRetrieveCategoriesOfOrder(input = id))
            .map { row, meta -> categoryDataHandler.all(row = row, meta = meta) }
            .first()
            .onErrorResume {
                logger.warn { "+OrderService -> retrieveCategoriesOfOneOrder -> error = ${it.message}" }
                throw it
            }

    override suspend fun editOrder(input: EditOrderInput, token: UUID): Optional<Order> =
        database.sql(queryHelper.onEditOrder(input = input, token = token))
            .map { row, meta -> OrderDBHandler.one(row = row, meta = meta) }
            .first()
            .handle { single: Optional<Order>, sink: SynchronousSink<Optional<Order>> ->
                if (single.isPresent) {
                    monitorPublisher.publish(order = OrderForSub(order = single.get()))
                    sink.next(single)
                } else {
                    sink.error(RuntimeException("НЕВОЗМОЖНОСТЬ РЕДАКТИРОВАНИЯ ЗАКАЗА"))
                }
            }
            .onErrorResume {
                logger.warn { "+OrderService -> editOrder -> error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { Optional.empty() }

    override suspend fun addCategoriesInOrder(input: AttachCategoryWithOrderInput, token: UUID): Optional<Order> =
        Flux.fromIterable(input.categories)
            .flatMap {
                val value = AddCategoryInOrderInput(
                    orderId = input.orderId,
                    categoryId = it.categoryId,
                    itemCount = it.itemCount ?: 0
                )
                database.sql(queryHelper.onAddCategoriesInOrder(input = value, token = token))
                    .map { row, meta -> OrderDBHandler.one(row = row, meta = meta) }
                    .first()
            }.last()
            .handle { single: Optional<Order>, sink: SynchronousSink<Optional<Order>> ->
                if (single.isPresent) {
                    monitorPublisher.publish(order = OrderForSub(order = single.get()))
                    sink.next(single)
                } else {
                    sink.error(RuntimeException("НЕВОЗМОЖНО ДОБАВИТЬ КАТЕГОРИЮ ПО ПОРЯДКУ"))
                }
            }
            .onErrorResume {
                logger.warn { "+OrderService -> addCategoriesInOrder -> error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { Optional.empty() }

    override suspend fun editCategoryOfOrder(
        input: EditCategoryOfOrderInput,
        token: UUID
    ): Optional<Order> = database.sql(queryHelper.onEditCategoryOfOrder(input = input, token = token))
        .map { row, meta -> OrderDBHandler.one(row = row, meta = meta) }
        .first()
        .handle { single: Optional<Order>, sink: SynchronousSink<Optional<Order>> ->
            if (single.isPresent) {
                monitorPublisher.publish(order = OrderForSub(order = single.get()))
                sink.next(single)
            } else {
                sink.error(RuntimeException("НЕВОЗМОЖНОСТЬ РЕДАКТИРОВАНИЯ КАТЕГОРИИ ПО ПОРЯДКУ"))
            }
        }
        .onErrorResume {
            logger.warn { "+---- OrderService -> editCategoryOfOrder -> error = ${it.message}" }
            throw it
        }
        .awaitFirstOrElse { Optional.empty() }

    override suspend fun retrieveOneOrder(id: UUID, token: UUID): Optional<Order> =
        database.sql(queryHelper.onRetrieveOneOrder(input = id, token = token))
            .map { row, meta -> OrderDBHandler.one(row = row, meta = meta) }
            .first()
            .onErrorResume {
                logger.warn { "+OrderService -> retrieveOneOrder -> error = ${it.message}" }
                throw it
            }.awaitFirstOrElse { Optional.empty() }

    override suspend fun onRetrieveItemsInCategoriesOfOrder(
        ids: Set<CategoryOfItem>,
        moduleId: UUID,
        page: Int,
        size: Int
    ): Map<CategoryOfItem, List<Item>> = Flux.fromIterable(ids)
        .filter { it?.orderId != null }
        .flatMap { ins ->
            return@flatMap database.sql(
                queryHelper.onRetrieveItemsInOrder(
                    orderId = ins.orderId!!,
                    categoryId = ins.id
                )
            ).map { row, meta -> ItemDBHandler.all(row = row, meta = meta) }
                .first()
                .map { AbstractMap.SimpleEntry(ins, it) }
        }
        .collect(Collectors.toList())
        .map {
            val map = mutableMapOf<CategoryOfItem, List<Item>>()
            it.forEach { element -> map[element.key] = element.value }
            return@map map.toMap()
        }
        .onErrorResume {
            logger.warn { "+OrderService -> onRetrieveItemsInCategoriesOfOrder -> error = ${it.message}" }
            throw it
        }
        .awaitFirstOrElse { emptyMap() }

    override suspend fun addItemsInOrder(input: ItemInOrder, token: UUID): Optional<Order> =
        Flux.fromIterable(input.itemIds ?: listOf())
            .filter {
                val uuid: UUID? = try {
                    UUID.fromString(it)
                } catch (e: Exception) {
                    logger.warn { e.message }
                    null
                }
                uuid != null
            }.flatMap {
                val subInput = ItemInOrderInput(orderId = input.orderId, itemId = UUID.fromString(it))
                database.sql(OrderDBQueries.addItemInOrder(input = subInput, token = token))
                    .map { row, meta -> OrderDBHandler.one(row = row, meta = meta) }
                    .first()
            }.collectList()
            .map { it.last() }
            .awaitFirstOrElse { Optional.empty() }

    override suspend fun editItemInOrder(input: EditItemInOrderInput, token: UUID): Optional<Order> =
        database.sql(OrderDBQueries.editItemInOrder(input = input, token = token))
            .map { row -> OrderDBHandler.one(row = row) }
            .first()
            .onErrorResume {
                logger.warn { "+OrderService->editItemInOrder->error = ${it.message}" }
                throw it
            }.awaitFirstOrElse { Optional.empty() }

    override suspend fun deleteItemsInOrder(input: ItemInOrder, token: UUID): Optional<Order> =
        Flux.fromIterable(input.itemIds ?: listOf())
            .filter {
                val uuid: UUID? = try {
                    UUID.fromString(it)
                } catch (e: Exception) {
                    logger.warn { e.message }
                    null
                }
                uuid != null
            }.flatMap {
                val subInput = ItemInOrderInput(orderId = input.orderId, itemId = UUID.fromString(it))
                database.sql(OrderDBQueries.deleteItemInOrder(input = subInput, token = token))
                    .map { row, meta -> OrderDBHandler.one(row = row, meta = meta) }
                    .first()
            }.collectList()
            .map { it.last() }
            .awaitFirstOrElse { Optional.empty() }

    override suspend fun retrieveItemInOrderById(itemId: UUID, orderId: UUID, token: UUID): Optional<Item> =
        database.sql(OrderDBQueries.retrieveOnItemInOrder(itemId = itemId, orderId = orderId, token = token))
            .map { row, meta -> ItemDBHandler.one(row = row, meta = meta) }
            .first()
            .onErrorResume {
                logger.warn { "+OrderService -> retrieveItemInOrderById -> error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { Optional.empty() }

    override suspend fun terminateAnalysis(id: UUID, token: UUID): Optional<Order> =
        database.sql(OrderDBQueries.terminateAnalysis(input = id, token = token))
            .map { row, meta -> OrderDBHandler.one(row = row, meta = meta) }
            .first()
            .onErrorResume {
                logger.warn { "+OrderService -> terminateAnalysis -> error = ${it.message}" }
                throw it
            }.awaitFirstOrElse { Optional.empty() }

    override suspend fun retrieveProgressionStepsByOrderId(orderId: UUID, token: UUID): List<ProgressionStep> =
        database.sql(OrderDBQueries.retrieveProgressionStepByOrderId(orderId = orderId, token = token))
            .map { row -> ProgressionStepDBHandler.all(row = row) }
            .first()
            .onErrorResume {
                logger.warn { "+OrderService -> retrieveProgressionStepsByOrderId -> error = ${it.message}" }
                throw it
            }.awaitFirstOrElse { emptyList() }

    override suspend fun importOrderFromJsonFile(environment: DataFetchingEnvironment): Optional<Boolean> =
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
                val list: List<OrderFromOld> = (obj as JSONArray).map { emp ->
                    OrderFromOld(
                        name = (emp as JSONObject)["name"] as String,
                        description = emp["description"] as String?
                    )
                }

                Flux.fromIterable(list)
            }
            .flatMap {
                it.flatMap { elt ->
                    database.sql(OrderDBQueries.addOldOrder(input = elt, token = UUID.randomUUID()))
                        .map { row -> row }
                        .first()
                }
            }
            .map {
                Optional.of(true)
            }.awaitFirstOrElse { Optional.of(false) }

    override suspend fun retrieveNumberOfActiveOrder(token: UUID): Optional<Int> =
        database.sql(OrderDBQueries.retrieveNumberOfActiveOrder(token = token))
            .map { row, meta -> OrderDBHandler.count(row = row, meta = meta) }
            .first()
            .map {
                Optional.ofNullable(it)
            }
            .onErrorResume {
                logger.warn { "+OrderService -> retrieveNumberOfActiveOrder -> error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { Optional.empty() }

    /**
     * @param companyId the reference to the company where the order belongs
     * @param token security aspect
     * @return {@link String}
     */
    override suspend fun retrieveNextOrderReference(companyId: UUID, token: UUID): String =
        retrieveNextReferenceImpl(companyId = companyId, token = token, database = database)

    /**
     * @param ids list of orders related id.
     * @param userId aspect security.
     * @return {@link Map<UUID, Optional<OrderType>>}
     */
    override suspend fun retrieveMyType(ids: Set<UUID>, userId: UUID): Map<UUID, Optional<OrderType>> =
        myType(ids = ids, database = database)

    /**
     * @param first number of element to retrieve
     * @param after last element in previous request
     * @param token request security aspect
     * @return {@link List<Order>}
     */
    override suspend fun myCompletedOrders(first: Int, after: UUID?, token: UUID): List<Order> =
        completedOrderFn(first = first, after = after, token = token, database = database)

    /**
     * @param departmentId reference to working group
     * @param first number of element to retrieve
     * @param after last element in previous request
     * @param token request security aspect
     * @return {@link List<Order>}
     */
    override suspend fun myOrdersByDepartmentId(
        departmentId: UUID,
        first: Int,
        after: UUID?,
        token: UUID,
        level: OrderLevel?
    ): List<Order> = orderByDepartmentIdFn(
        first = first,
        after = after,
        token = token,
        database = database,
        departmentId = departmentId,
        level = level
    )

    override suspend fun completionLineGraph(year: Int, token: UUID): Optional<OrderCompletionLine> =
        completionLineGraphUseCase(database = database, year = year, token = token)
}