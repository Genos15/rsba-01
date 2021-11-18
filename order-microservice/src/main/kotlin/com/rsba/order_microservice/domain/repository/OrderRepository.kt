package  com.rsba.order_microservice.domain.repository

import com.rsba.order_microservice.domain.input.*
import com.rsba.order_microservice.domain.model.*
import graphql.schema.DataFetchingEnvironment
import reactor.core.publisher.Mono
import java.util.*

interface OrderRepository {

    suspend fun createOrder(input: CreateOrderInput, token: UUID): Optional<Order>

    suspend fun deleteOrder(input: UUID, token: UUID): Int

    suspend fun onRetrieveAllOrder(first: Int, after: UUID?, token: UUID): MutableList<Order>

    suspend fun orderByUserToken(
        first: Int,
        after: UUID?,
        token: UUID,
        level: OrderLevel? = null,
    ): List<Order>

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
    ): Map<UUID, List<ItemCategory>>

    suspend fun retrieveItemsInOrder(
        ids: Set<UUID>,
        userId: UUID,
        page: Int,
        size: Int
    ): Map<UUID, List<Item>>

    fun retrieveCategoriesOfOneOrder(id: UUID): Mono<MutableList<ItemCategory>>

    suspend fun editOrder(input: EditOrderInput, token: UUID): Optional<Order>

    suspend fun addCategoriesInOrder(input: AttachCategoryWithOrderInput, token: UUID): Optional<Order>

    suspend fun editCategoryOfOrder(input: EditCategoryOfOrderInput, token: UUID): Optional<Order>

    suspend fun retrieveOneOrder(id: UUID, token: UUID): Optional<Order>

    suspend fun onRetrieveItemsInCategoriesOfOrder(
        ids: Set<ItemCategory>,
        moduleId: UUID,
        page: Int,
        size: Int
    ): Map<ItemCategory, List<Item>>

    suspend fun addItemsInOrder(input: ItemInOrder, token: UUID): Optional<Order>

    suspend fun editItemInOrder(input: EditItemInOrderInput, token: UUID): Optional<Order>

    suspend fun deleteItemsInOrder(input: ItemInOrder, token: UUID): Optional<Order>

    suspend fun retrieveItemInOrderById(itemId: UUID, orderId: UUID, token: UUID): Optional<Item>

    suspend fun terminateAnalysis(id: UUID, token: UUID): Optional<Order>

    suspend fun retrieveProgressionStepsByOrderId(orderId: UUID, token: UUID): List<ProgressionStep>

    suspend fun importOrderFromJsonFile(environment: DataFetchingEnvironment): Optional<Boolean>

    suspend fun retrieveNumberOfActiveOrder(token: UUID): Optional<Int>

    suspend fun retrieveNextOrderReference(companyId: UUID, token: UUID): String

    suspend fun retrieveMyType(
        ids: Set<UUID>,
        userId: UUID
    ): Map<UUID, Optional<OrderType>>

    suspend fun myCompletedOrders(first: Int, after: UUID?, token: UUID): List<Order>

    suspend fun myOrdersByDepartmentId(
        departmentId: UUID,
        first: Int,
        after: UUID?,
        token: UUID,
        level: OrderLevel? = null,
    ): List<Order>


    suspend fun completionLineGraph(year: Int, token: UUID): Optional<OrderCompletionLine>

    suspend fun toCreateOrEdit(input: OrderInput, action: MutationAction? = null, token: UUID): Optional<Order>

    suspend fun toDelete(input: UUID, token: UUID): Boolean

    suspend fun find(id: UUID, token: UUID): Optional<Order>

    suspend fun retrieve(
        first: Int,
        after: UUID? = null,
        status: OrderStatus? = null,
        layer: OrderLayer? = null,
        token: UUID
    ): List<Order>

    suspend fun search(
        input: String,
        first: Int,
        after: UUID? = null,
        status: OrderStatus? = null,
        layer: OrderLayer? = null,
        token: UUID
    ): List<Order>

    suspend fun count(token: UUID, status: OrderStatus? = null): Int

    suspend fun items(
        ids: Set<UUID>,
        first: Int = 1000,
        after: UUID? = null,
        token: UUID = UUID.randomUUID()
    ): Map<UUID, List<Item>>

    suspend fun tasks(
        ids: Set<UUID>,
        first: Int = 1000,
        after: UUID? = null,
        token: UUID = UUID.randomUUID()
    ): Map<UUID, List<Task>>

    suspend fun technologies(
        ids: Set<UUID>,
        first: Int = 1000,
        after: UUID? = null,
        token: UUID = UUID.randomUUID()
    ): Map<UUID, List<Technology>>

    suspend fun parameters(
        ids: Set<UUID>,
        first: Int = 1000,
        after: UUID? = null,
        token: UUID = UUID.randomUUID()
    ): Map<UUID, List<Parameter>>

    suspend fun categories(
        ids: Set<UUID>,
        first: Int = 1000,
        after: UUID? = null,
        token: UUID = UUID.randomUUID()
    ): Map<UUID, List<ItemCategory>>

    suspend fun worklogs(
        ids: Set<UUID>,
        first: Int = 1000,
        after: UUID? = null,
        token: UUID = UUID.randomUUID()
    ): Map<UUID, List<Worklog>>

    suspend fun customer(ids: Set<UUID>, token: UUID = UUID.randomUUID()): Map<UUID, Optional<Customer>>

    suspend fun manager(ids: Set<UUID>, token: UUID = UUID.randomUUID()): Map<UUID, Optional<Agent>>

    suspend fun agent(ids: Set<UUID>, token: UUID = UUID.randomUUID()): Map<UUID, Optional<Agent>>

    suspend fun type(ids: Set<UUID>, token: UUID = UUID.randomUUID()): Map<UUID, Optional<OrderType>>

}
