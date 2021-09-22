package  com.rsba.monitor_ms.service

import com.rsba.monitor_ms.database.AgentDataHandler
import com.rsba.monitor_ms.database.CategoryDataHandler
import  com.rsba.monitor_ms.domain.model.Order
import  com.rsba.monitor_ms.database.OrderDataHandler
import  com.rsba.monitor_ms.database.OrderDatabaseQuery
import com.rsba.monitor_ms.domain.input.*
import com.rsba.monitor_ms.domain.model.Agent
import com.rsba.monitor_ms.domain.model.CategoryOfItemInOrder
import  com.rsba.monitor_ms.repository.OrderRepository
import kotlinx.coroutines.reactive.awaitFirstOrElse
import mu.KLogger
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.SynchronousSink
import java.util.*
import java.util.stream.Collectors

@Service
class OrderService(
    private val logger: KLogger,
    private val database: DatabaseClient,
    private val queryHelper: OrderDatabaseQuery,
    private val dataHandler: OrderDataHandler,
    private val categoryDataHandler: CategoryDataHandler,
    private val agentDataHandler: AgentDataHandler
) : OrderRepository {

    override suspend fun createOrder(input: CreateOrderInput, token: UUID): Optional<Order> {
        logger.warn { "+------ OrderService -> createOrder" }
        return database.sql(queryHelper.onCreateOrder(input = input, token = token))
            .map { row, meta -> dataHandler.one(row = row, meta = meta) }
            .first()
            .onErrorResume { Mono.empty() }
            .awaitFirstOrElse { Optional.empty() }
    }

    override suspend fun deleteOrder(input: UUID, token: UUID): Int {
        logger.warn { "+------ OrderService -> deleteOrder" }
        return database.sql(queryHelper.onDeleteOrder(input = input, token = token))
            .map { row, meta -> dataHandler.count(row = row, meta = meta) }
            .first()
            .onErrorResume { Mono.empty() }
            .awaitFirstOrElse { 0 }
    }

    override suspend fun onRetrieveAllOrder(token: UUID): MutableList<Order> {
        logger.warn { "+------ OrderService -> onRetrieveAllOrder" }
        return database.sql(queryHelper.onRetrieveAllOrder(token = token))
            .map { row, meta -> dataHandler.all(row = row, meta = meta) }
            .first()
            .onErrorResume { Mono.empty() }
            .awaitFirstOrElse { mutableListOf() }
    }


    override fun all(token: UUID): Flux<MutableList<Order>> {
        logger.warn { "+------ OrderService -> all" }
        return database.sql(queryHelper.onRetrieveAllOrder(token = token))
            .map { row, meta -> dataHandler.all(row = row, meta = meta) }
            .all()
            .onErrorResume { Flux.empty() }
    }

    override suspend fun retrieveManagerOfOrder(
        orderIds: Set<UUID>,
        userId: UUID,
        page: Int,
        size: Int
    ): Map<UUID, Agent?> {
        logger.warn { "+---- OrderService -> retrieveManagerOfOrder" }
        return Flux.fromIterable(orderIds)
            .flatMap { id ->
                return@flatMap database.sql(queryHelper.onRetrieveManagerOfOrder(input = id))
                    .map { row, meta -> agentDataHandler.one(row = row, meta = meta) }
                    .first()
                    .handle { single: Optional<Agent>, sink: SynchronousSink<Agent> ->
                        if (single.isPresent) {
                            sink.next(single.get())
                        } else {
                            sink.error(RuntimeException("MANAGER NOT FOUND"))
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
                logger.warn { "error2 = ${it.message}" }
                Mono.empty()
            }
            .awaitFirstOrElse { emptyMap() }
    }

    override suspend fun retrieveAgentOfOrder(
        orderIds: Set<UUID>,
        userId: UUID,
        page: Int,
        size: Int
    ): Map<UUID, Agent?> {
        logger.warn { "+---- OrderService -> retrieveAgentOfOrder" }
        return Flux.fromIterable(orderIds)
            .flatMap { id ->
                return@flatMap database.sql(queryHelper.onRetrieveAgentOfOrder(input = id))
                    .map { row, meta -> agentDataHandler.one(row = row, meta = meta) }
                    .first()
                    .handle { single: Optional<Agent>, sink: SynchronousSink<Agent> ->
                        if (single.isPresent) {
                            sink.next(single.get())
                        } else {
                            sink.error(RuntimeException("AGENT NOT FOUND"))
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
                logger.warn { "error2 = ${it.message}" }
                Mono.empty()
            }
            .awaitFirstOrElse { emptyMap() }
    }

    override suspend fun retrieveCategoriesOfOrder(
        orderIds: Set<UUID>,
        userId: UUID,
        page: Int,
        size: Int
    ): Map<UUID, List<CategoryOfItemInOrder>> {
        logger.warn { "+---- OrderService -> retrieveCategoriesOfOrder" }
        return Flux.fromIterable(orderIds)
            .flatMap { id ->
                return@flatMap database.sql(queryHelper.onRetrieveCategoriesOfOrder(input = id))
                    .map { row, meta -> categoryDataHandler.all(row = row, meta = meta) }
                    .first()
                    .map { AbstractMap.SimpleEntry(id, it) }
            }
            .collect(Collectors.toList())
            .map {
                val map = mutableMapOf<UUID, List<CategoryOfItemInOrder>>()
                it.forEach { element -> map[element.key] = element.value }
                return@map map.toMap()
            }
            .onErrorResume { Mono.empty() }
            .awaitFirstOrElse { emptyMap() }
    }

    override suspend fun editOrder(input: EditOrderInput, token: UUID): Optional<Order> {
        logger.warn { "+---- OrderService -> editOrder" }
        return database.sql(queryHelper.onEditOrder(input = input, token = token))
            .map { row, meta -> dataHandler.one(row = row, meta = meta) }
            .first()
            .onErrorResume { Mono.empty() }
            .awaitFirstOrElse { Optional.empty() }
    }

    override suspend fun addCategoriesInOrder(input: AttachCategoryWithOrderInput, token: UUID): Optional<Order> {
        logger.warn { "+---- OrderService -> addCategoriesInOrder" }
        return Flux.fromIterable(input.categories)
            .flatMap {
                val value = AddCategoryInOrderInput(
                    orderId = input.orderId,
                    categoryId = it.categoryId,
                    itemCount = it.itemCount ?: 0
                )
                database.sql(queryHelper.onAddCategoriesInOrder(input = value, token = token))
                    .map { row, meta -> dataHandler.one(row = row, meta = meta) }
                    .first()
            }.last()
            .onErrorResume {
                logger.warn { "+---- OrderService -> addCategoriesInOrder -> error = ${it.message}" }
                Mono.error { RuntimeException(it.message) }
            }
            .awaitFirstOrElse { Optional.empty() }

    }

    override suspend fun editCategoryOfOrder(
        input: EditCategoryOfOrderInput,
        token: UUID
    ): Optional<Order> {
        logger.warn { "+---- OrderService -> editCategoryOfOrder" }
        return database.sql(queryHelper.onEditCategoryOfOrder(input = input, token = token))
            .map { row, meta -> dataHandler.one(row = row, meta = meta) }
            .first()
            .onErrorResume {
                logger.warn { "+---- OrderService -> editCategoryOfOrder -> error = ${it.message}" }
                Mono.error { RuntimeException(it.message) }
            }
            .awaitFirstOrElse { Optional.empty() }
    }

    override suspend fun retrieveOneOrder(id: UUID, token: UUID): Optional<Order> {
        logger.warn { "+---- OrderService -> retrieveOneOrder" }
        return database.sql(queryHelper.onRetrieveOneOrder(input = id, token = token))
            .map { row, meta -> dataHandler.one(row = row, meta = meta) }
            .first()
            .onErrorResume {
                logger.warn { "+---- OrderService -> retrieveOneOrder -> error = ${it.message}" }
                Mono.error { RuntimeException(it.message) }
            }.awaitFirstOrElse { Optional.empty() }
    }

}