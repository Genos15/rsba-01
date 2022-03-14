package  com.rsba.order_microservice.data.service.implementation

import com.rsba.order_microservice.domain.input.*
import com.rsba.order_microservice.domain.model.*
import  com.rsba.order_microservice.domain.repository.OrderRepository
import com.rsba.order_microservice.domain.usecase.common.*
import com.rsba.order_microservice.domain.usecase.custom.order.*
import graphql.relay.Connection
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.*

@Service
class OrderService(
    private val database: DatabaseClient,
    private val completionLineGraphUseCase: RetrieveOrderCompletionLineGraphUseCase,
    @Qualifier("create_edit_order") private val createOrEditUseCase: CreateOrEditUseCase<OrderInput, Order>,
    @Qualifier("delete_order") private val deleteUseCase: DeleteUseCase<Order>,
    @Qualifier("find_order") private val findUseCase: FindUseCase<Order>,
    @Qualifier("retrieve_order") private val retrieveUseCase: RetrieveUseCase<Order>,
    @Qualifier("search_order") private val searchUseCase: SearchUseCase<Order>,
    @Qualifier("count_order") private val countUseCase: CountUseCase,
    private val potentialReferenceNumberUseCase: PotentialReferenceNumberUseCase,
    private val itemsUseCase: RetrieveItemsUseCase,
    private val tasksUseCase: RetrieveTasksUseCase,
    private val agentUseCase: RetrieveAgentUseCase,
    private val managerUseCase: RetrieveManagerUseCase,
    private val customerUseCase: RetrieveCustomerUseCase,
    private val typeUseCase: RetrieveTypeUseCase,
    private val taskUseCase: FindTaskUseCase,
    @Qualifier("find_order_item") private val itemUseCase: FindItemUseCase,
    @Qualifier("retrieve_technologies_order") private val technologiesUseCase: RetrieveTechnologiesUseCase,
    private val searchGlobalParametersUseCase: SearchGlobalParametersUseCase,
    private val searchGlobalItemsUseCase: SearchGlobalItemsUseCase,
    private val searchGlobalTasksUseCase: SearchGlobalTasksUseCase,
    private val searchGlobalTechnologiesUseCase: SearchGlobalTechnologiesUseCase,
    private val editItemUseCase: EditItemUseCase,
    private val editTaskUseCase: EditTaskUseCase,
    private val findOrderStatisticsUseCase: FindOrderStatisticsUseCase,
    private val retrieveDepartmentStatisticsUseCase: RetrieveDepartmentStatisticsUseCase,
    private val retrieveItemCategoryStatisticsUseCase: RetrieveItemCategoryStatisticsUseCase,
    @Qualifier("retrieve_order_parameters") private val retrieveParametersUseCase: RetrieveParametersUseCase,
    @Qualifier("retrieve_orders_worklogs") private val retrieveWorklogsUseCase: RetrieveWorklogsUseCase
) : OrderRepository {

    override suspend fun completionLineGraph(year: Int, token: UUID): Optional<OrderCompletionLine> =
        completionLineGraphUseCase(database = database, year = year, token = token)

    override suspend fun toCreateOrEdit(input: OrderInput, action: MutationAction?, token: UUID): Optional<Order> =
        createOrEditUseCase(database = database, input = input, token = token, action = action)

    override suspend fun toDelete(input: UUID, token: UUID): Boolean =
        deleteUseCase(database = database, input = input, token = token)

    override suspend fun find(id: UUID, token: UUID): Optional<Order> =
        findUseCase(database = database, id = id, token = token)

    override suspend fun retrieve(
        first: Int,
        after: UUID?,
        status: OrderStatus?,
        layer: OrderLayer?,
        token: UUID
    ): List<Order> =
        retrieveUseCase(
            database = database,
            first = first,
            after = after,
            token = token,
            layer = layer,
            status = status
        )

    override suspend fun search(
        input: String,
        first: Int,
        after: UUID?,
        status: OrderStatus?,
        layer: OrderLayer?,
        token: UUID
    ): List<Order> =
        searchUseCase(
            database = database,
            first = first,
            after = after,
            token = token,
            input = input,
            layer = layer,
            status = status
        )

    override suspend fun count(token: UUID, status: OrderStatus?): Int =
        countUseCase(database = database, token = token, status = status)

    override suspend fun items(
        ids: Set<UUID>,
        first: Int,
        parentId: UUID?,
        after: UUID?,
        token: UUID
    ): Map<UUID, List<Item>> =
        itemsUseCase(ids = ids, first = first, after = after, token = token, database = database, parentId = parentId)

    override suspend fun tasks(
        ids: Set<UUID>,
        first: Int,
        parentId: UUID?,
        after: UUID?,
        token: UUID
    ): Map<UUID, List<Task>> =
        tasksUseCase(ids = ids, first = first, after = after, token = token, database = database, parentId = parentId)

    override suspend fun technologies(
        ids: Set<UUID>,
        first: Int,
        parentId: UUID?,
        after: UUID?,
        token: UUID
    ): Map<UUID, List<Technology>> = technologiesUseCase(
        ids = ids,
        first = first,
        after = after,
        token = token,
        database = database,
        parentId = parentId
    )

    override suspend fun parameters(ids: Set<UUID>, first: Int, after: UUID?, token: UUID): Map<UUID, List<Parameter>> =
        retrieveParametersUseCase(database = database, ids = ids, token = token, first = first, after = after)

    override suspend fun categories(
        ids: Set<UUID>,
        first: Int,
        after: UUID?,
        token: UUID
    ): Map<UUID, List<ItemCategory>> = emptyMap()

    override suspend fun worklogs(
        ids: Set<UUID>,
        first: Int,
        after: UUID?,
        rangeStart: OffsetDateTime?,
        rangeEnd: OffsetDateTime?,
        token: UUID
    ): Map<UUID, List<Worklog>> =
        retrieveWorklogsUseCase(
            ids = ids,
            token = token,
            first = first,
            after = after,
            rangeStart = rangeStart,
            rangeEnd = rangeEnd,
        )

    override suspend fun customer(ids: Set<UUID>, token: UUID): Map<UUID, Optional<Customer>> =
        customerUseCase(ids = ids, token = token, database = database)

    override suspend fun item(ids: Set<UUID>, token: UUID): Map<UUID, Optional<Item>> =
        itemUseCase(ids = ids, token = token, database = database)

    override suspend fun task(ids: Set<UUID>, token: UUID): Map<UUID, Optional<Task>> =
        taskUseCase(ids = ids, token = token, database = database)

    override suspend fun manager(ids: Set<UUID>, token: UUID): Map<UUID, Optional<Agent>> =
        managerUseCase(ids = ids, token = token, database = database)

    override suspend fun agent(ids: Set<UUID>, token: UUID): Map<UUID, Optional<Agent>> =
        agentUseCase(ids = ids, token = token, database = database)

    override suspend fun type(ids: Set<UUID>, token: UUID): Map<UUID, Optional<OrderType>> =
        typeUseCase(ids = ids, token = token, database = database)

    override suspend fun potentialReferenceNumber(companyId: UUID, token: UUID): String =
        potentialReferenceNumberUseCase(token = token, database = database)

    override suspend fun searchGlobal(input: OrderSearchInputValue, token: UUID): OrderSearchInstance =
        OrderSearchInstance(input = input)

    override suspend fun searchGlobalItems(
        ids: Set<OrderSearchInputValue>,
        token: UUID
    ): Map<OrderSearchInputValue, Connection<Item>> =
        searchGlobalItemsUseCase(database = database, ids = ids, token = token)

    override suspend fun searchGlobalTasks(
        ids: Set<OrderSearchInputValue>,
        token: UUID
    ): Map<OrderSearchInputValue, Connection<Task>> =
        searchGlobalTasksUseCase(database = database, ids = ids, token = token)

    override suspend fun searchGlobalTechnologies(
        ids: Set<OrderSearchInputValue>,
        token: UUID
    ): Map<OrderSearchInputValue, Connection<Technology>> =
        searchGlobalTechnologiesUseCase(database = database, ids = ids, token = token)

    override suspend fun searchGlobalParameters(
        ids: Set<OrderSearchInputValue>,
        token: UUID
    ): Map<OrderSearchInputValue, Connection<Parameter>> =
        searchGlobalParametersUseCase(database = database, ids = ids, token = token)

    override suspend fun toEditTask(input: TaskInput, action: MutationAction?, token: UUID): Optional<Task> =
        editTaskUseCase(database = database, input = input, token = token, action = action)

    override suspend fun toEditItem(input: ItemInput, action: MutationAction?, token: UUID): Optional<Item> =
        editItemUseCase(database = database, input = input, token = token)

    override suspend fun statistics(ids: Set<UUID>, token: UUID): Map<UUID, Optional<OrderStatistics>> =
        findOrderStatisticsUseCase(database = database, ids = ids, token = token)

    override suspend fun departmentStatistics(
        ids: Set<UUID>,
        first: Int,
        after: UUID?,
        token: UUID
    ): Map<UUID, List<DepartmentStatistics>> =
        retrieveDepartmentStatisticsUseCase(
            ids = ids,
            first = first,
            after = after,
            token = token,
            database = database
        )

    override suspend fun itemCategoryStatistics(
        ids: Set<UUID>,
        first: Int,
        after: UUID?,
        token: UUID
    ): Map<UUID, List<ItemCategoryStatistics>> =
        retrieveItemCategoryStatisticsUseCase(
            ids = ids,
            first = first,
            after = after,
            token = token,
            database = database
        )
}