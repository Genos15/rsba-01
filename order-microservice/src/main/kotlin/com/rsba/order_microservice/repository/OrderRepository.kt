package  com.rsba.order_microservice.repository

import com.rsba.order_microservice.domain.input.*
import com.rsba.order_microservice.domain.model.*
import graphql.schema.DataFetchingEnvironment
import reactor.core.publisher.Mono
import java.util.*

interface OrderRepository {

    suspend fun createOrder(input: CreateOrderInput, token: UUID): Optional<Order>

    suspend fun deleteOrder(input: UUID, token: UUID): Int

    suspend fun onRetrieveAllOrder(first: Int, after: UUID?, token: UUID): MutableList<Order>

    suspend fun orderByUserToken(first: Int, after: UUID?, token: UUID): List<Order>

    suspend fun retrieveManagerOfOrder(
        orderIds: Set<UUID>,
        userId: UUID,
        page: Int,
        size: Int
    ): Map<UUID, Agent?>

    suspend fun retrieveAgentOfOrder(
        orderIds: Set<UUID>,
        userId: UUID,
        page: Int,
        size: Int
    ): Map<UUID, Agent?>

    suspend fun retrieveCategoriesOfOrder(
        orderIds: Set<UUID>,
        userId: UUID,
        page: Int,
        size: Int
    ): Map<UUID, List<CategoryOfItemInOrder>>

    suspend fun retrieveCategoriesInOrder(
        ids: Set<UUID>,
        userId: UUID,
        page: Int,
        size: Int
    ): Map<UUID, List<CategoryOfItem>>

    suspend fun retrieveItemsInOrder(
        ids: Set<UUID>,
        userId: UUID,
        page: Int,
        size: Int
    ): Map<UUID, List<Item>>

    fun retrieveCategoriesOfOneOrder(id: UUID): Mono<MutableList<CategoryOfItem>>

    suspend fun editOrder(input: EditOrderInput, token: UUID): Optional<Order>

    suspend fun addCategoriesInOrder(input: AttachCategoryWithOrderInput, token: UUID): Optional<Order>

    suspend fun editCategoryOfOrder(input: EditCategoryOfOrderInput, token: UUID): Optional<Order>

    suspend fun retrieveOneOrder(id: UUID, token: UUID): Optional<Order>

    suspend fun onRetrieveItemsInCategoriesOfOrder(
        ids: Set<CategoryOfItem>,
        moduleId: UUID,
        page: Int,
        size: Int
    ): Map<CategoryOfItem, List<Item>>

    suspend fun addItemsInOrder(input: ItemInOrder, token: UUID): Optional<Order>

    suspend fun editItemInOrder(input: EditItemInOrderInput, token: UUID): Optional<Order>

    suspend fun deleteItemsInOrder(input: ItemInOrder, token: UUID): Optional<Order>

    suspend fun retrieveItemInOrderById(itemId: UUID, orderId: UUID, token: UUID): Optional<Item>

    suspend fun terminateAnalysis(id: UUID, token: UUID): Optional<Order>

    suspend fun retrieveProgressionStepsByOrderId(orderId: UUID, token: UUID): List<ProgressionStep>

    suspend fun importOrderFromJsonFile(environment: DataFetchingEnvironment): Optional<Boolean>

    suspend fun retrieveNumberOfActiveOrder(token: UUID): Optional<Int>

}
