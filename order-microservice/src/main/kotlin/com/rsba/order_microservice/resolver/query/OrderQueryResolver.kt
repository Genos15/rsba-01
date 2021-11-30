package  com.rsba.order_microservice.resolver.query

import  com.rsba.order_microservice.aspect.AdminSecured
import com.rsba.order_microservice.domain.model.*
import  com.rsba.order_microservice.domain.repository.OrderRepository
import com.rsba.order_microservice.domain.security.TokenAnalyzer
import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.relay.*
import org.springframework.stereotype.Component

import graphql.schema.DataFetchingEnvironment
import java.util.*


@Component
class OrderQueryResolver(private val service: OrderRepository, private val deduct: TokenAnalyzer) :
    GraphQLQueryResolver, GenericRetrieveConnection {

    @AdminSecured
    suspend fun retrieveOrders(
        first: Int,
        after: UUID? = null,
        status: OrderStatus? = null,
        layer: OrderLayer? = null,
        environment: DataFetchingEnvironment
    ): Connection<Order>? = perform(
        entries = service.retrieve(
            first = first,
            after = after,
            token = deduct(environment = environment),
            status = status,
            layer = layer
        ),
        first = first,
        after = after,
    )

    @AdminSecured
    suspend fun searchOrders(
        input: String,
        first: Int,
        after: UUID? = null,
        status: OrderStatus? = null,
        layer: OrderLayer? = null,
        environment: DataFetchingEnvironment
    ): Connection<Order> = perform(
        entries = service.search(
            input = input,
            first = first,
            after = after,
            token = deduct(environment = environment),
            status = status,
            layer = layer
        ),
        first = first,
        after = after
    )

    suspend fun findOrder(id: UUID, environment: DataFetchingEnvironment): Optional<Order> =
        service.find(id = id, token = deduct(environment = environment))

    suspend fun countOrders(status: OrderStatus? = null, environment: DataFetchingEnvironment): Int =
        service.count(token = deduct(environment = environment), status = status)

    suspend fun retrieveOrderItems(
        id: UUID,
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<Item> = perform(
        entries = service.items(
            ids = setOf(id),
            token = deduct(environment = environment),
            first = first,
            after = after
        ),
        first = first,
        after = after,
        id = id
    )

    suspend fun retrieveOrderTasks(
        id: UUID,
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<Task> = perform(
        entries = service.tasks(
            ids = setOf(id),
            token = deduct(environment = environment),
            first = first,
            after = after
        ),
        first = first,
        after = after,
        id = id
    )

    suspend fun retrieveOrderTechnologies(
        id: UUID,
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<Technology> = perform(
        entries = service.technologies(
            ids = setOf(id),
            token = deduct(environment = environment),
            first = first,
            after = after
        ),
        first = first,
        after = after,
        id = id
    )

    suspend fun retrieveOrderParameters(
        id: UUID,
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<Parameter> = perform(
        entries = service.parameters(
            ids = setOf(id),
            token = deduct(environment = environment),
            first = first,
            after = after
        ),
        first = first,
        after = after,
        id = id
    )

    suspend fun retrieveOrderCategories(
        id: UUID,
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<ItemCategory> = perform(
        entries = service.categories(
            ids = setOf(id),
            token = deduct(environment = environment),
            first = first,
            after = after
        ),
        first = first,
        after = after,
        id = id
    )

    suspend fun retrieveOrderWorklogs(
        id: UUID,
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<Worklog> = perform(
        entries = service.worklogs(
            ids = setOf(id),
            token = deduct(environment = environment),
            first = first,
            after = after
        ),
        first = first,
        after = after,
        id = id
    )

    suspend fun findOrderCustomer(id: UUID, environment: DataFetchingEnvironment): Optional<Customer> = perform(
        entries = service.customer(ids = setOf(id), token = deduct(environment = environment)),
        id = id
    )

    suspend fun findOrderManager(id: UUID, environment: DataFetchingEnvironment): Optional<Agent> = perform(
        entries = service.manager(ids = setOf(id), token = deduct(environment = environment)),
        id = id
    )

    suspend fun findOrderAgent(id: UUID, environment: DataFetchingEnvironment): Optional<Agent> = perform(
        entries = service.agent(ids = setOf(id), token = deduct(environment = environment)),
        id = id
    )

    suspend fun findOrder__Type(id: UUID, environment: DataFetchingEnvironment): Optional<OrderType> = perform(
        entries = service.type(ids = setOf(id), token = deduct(environment = environment)),
        id = id
    )

    suspend fun potentialReferenceNumber(environment: DataFetchingEnvironment): String =
        service.potentialReferenceNumber(token = deduct(environment = environment))

}