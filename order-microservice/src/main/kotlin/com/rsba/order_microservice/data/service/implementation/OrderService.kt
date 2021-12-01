package  com.rsba.order_microservice.data.service.implementation

import com.rsba.order_microservice.domain.input.*
import com.rsba.order_microservice.domain.model.*
import  com.rsba.order_microservice.domain.repository.OrderRepository
import com.rsba.order_microservice.domain.usecase.common.*
import com.rsba.order_microservice.domain.usecase.custom.order.PotentialReferenceNumberUseCase
import com.rsba.order_microservice.domain.usecase.custom.order.RetrieveOrderCompletionLineGraphUseCase
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
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
    private val potentialReferenceNumberUseCase: PotentialReferenceNumberUseCase
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

    override suspend fun items(ids: Set<UUID>, first: Int, after: UUID?, token: UUID): Map<UUID, List<Item>> =
        emptyMap()

    override suspend fun tasks(ids: Set<UUID>, first: Int, after: UUID?, token: UUID): Map<UUID, List<Task>> =
        emptyMap()

    override suspend fun technologies(
        ids: Set<UUID>,
        first: Int,
        after: UUID?,
        token: UUID
    ): Map<UUID, List<Technology>> = emptyMap()

    override suspend fun parameters(ids: Set<UUID>, first: Int, after: UUID?, token: UUID): Map<UUID, List<Parameter>> =
        emptyMap()

    override suspend fun categories(
        ids: Set<UUID>,
        first: Int,
        after: UUID?,
        token: UUID
    ): Map<UUID, List<ItemCategory>> = emptyMap()

    override suspend fun worklogs(ids: Set<UUID>, first: Int, after: UUID?, token: UUID): Map<UUID, List<Worklog>> =
        emptyMap()

    override suspend fun customer(ids: Set<UUID>, token: UUID): Map<UUID, Optional<Customer>> = emptyMap()

    override suspend fun manager(ids: Set<UUID>, token: UUID): Map<UUID, Optional<Agent>> =
        emptyMap()

    override suspend fun agent(ids: Set<UUID>, token: UUID): Map<UUID, Optional<Agent>> =
        emptyMap()

    override suspend fun type(ids: Set<UUID>, token: UUID): Map<UUID, Optional<OrderType>> =
        emptyMap()

    override suspend fun potentialReferenceNumber(companyId: UUID, token: UUID): String =
        potentialReferenceNumberUseCase(token = token, database = database)
}