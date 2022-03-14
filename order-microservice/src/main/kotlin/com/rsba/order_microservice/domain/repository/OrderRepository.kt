package  com.rsba.order_microservice.domain.repository

import com.rsba.order_microservice.domain.input.*
import com.rsba.order_microservice.domain.model.*
import graphql.relay.Connection
import java.time.OffsetDateTime
import java.util.*

interface OrderRepository {

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
        parentId: UUID? = null,
        after: UUID? = null,
        token: UUID = UUID.randomUUID()
    ): Map<UUID, List<Item>>

    suspend fun tasks(
        ids: Set<UUID>,
        first: Int = 1000,
        parentId: UUID? = null,
        after: UUID? = null,
        token: UUID = UUID.randomUUID()
    ): Map<UUID, List<Task>>

    suspend fun technologies(
        ids: Set<UUID>,
        first: Int = 1000,
        parentId: UUID? = null,
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

    suspend fun customer(ids: Set<UUID>, token: UUID = UUID.randomUUID()): Map<UUID, Optional<Customer>>

    suspend fun item(ids: Set<UUID>, token: UUID = UUID.randomUUID()): Map<UUID, Optional<Item>>

    suspend fun task(ids: Set<UUID>, token: UUID = UUID.randomUUID()): Map<UUID, Optional<Task>>

    suspend fun manager(ids: Set<UUID>, token: UUID = UUID.randomUUID()): Map<UUID, Optional<Agent>>

    suspend fun agent(ids: Set<UUID>, token: UUID = UUID.randomUUID()): Map<UUID, Optional<Agent>>

    suspend fun type(ids: Set<UUID>, token: UUID = UUID.randomUUID()): Map<UUID, Optional<OrderType>>

    suspend fun potentialReferenceNumber(companyId: UUID = UUID.randomUUID(), token: UUID): String

    suspend fun searchGlobal(
        input: OrderSearchInputValue,
        token: UUID = UUID.randomUUID()
    ): OrderSearchInstance

    suspend fun searchGlobalItems(
        ids: Set<OrderSearchInputValue>,
        token: UUID = UUID.randomUUID()
    ): Map<OrderSearchInputValue, Connection<Item>>

    suspend fun searchGlobalTasks(
        ids: Set<OrderSearchInputValue>,
        token: UUID = UUID.randomUUID()
    ): Map<OrderSearchInputValue, Connection<Task>>

    suspend fun searchGlobalTechnologies(
        ids: Set<OrderSearchInputValue>,
        token: UUID = UUID.randomUUID()
    ): Map<OrderSearchInputValue, Connection<Technology>>

    suspend fun searchGlobalParameters(
        ids: Set<OrderSearchInputValue>,
        token: UUID = UUID.randomUUID()
    ): Map<OrderSearchInputValue, Connection<Parameter>>

    suspend fun toEditTask(input: TaskInput, action: MutationAction? = null, token: UUID): Optional<Task>

    suspend fun toEditItem(input: ItemInput, action: MutationAction? = null, token: UUID): Optional<Item>

    suspend fun statistics(ids: Set<UUID>, token: UUID = UUID.randomUUID()): Map<UUID, Optional<OrderStatistics>>

    suspend fun departmentStatistics(
        ids: Set<UUID>,
        first: Int = 1000,
        after: UUID? = null,
        token: UUID = UUID.randomUUID()
    ): Map<UUID, List<DepartmentStatistics>>

    suspend fun itemCategoryStatistics(
        ids: Set<UUID>,
        first: Int = 1000,
        after: UUID? = null,
        token: UUID = UUID.randomUUID()
    ): Map<UUID, List<ItemCategoryStatistics>>

    suspend fun worklogs(
        ids: Set<UUID>,
        first: Int = 1000,
        after: UUID? = null,
        rangeStart: OffsetDateTime? = null,
        rangeEnd: OffsetDateTime? = null,
        token: UUID = UUID.randomUUID()
    ): Map<UUID, List<Worklog>>
}
