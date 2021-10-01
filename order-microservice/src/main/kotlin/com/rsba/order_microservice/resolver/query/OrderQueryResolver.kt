package  com.rsba.order_microservice.resolver.query

import  com.rsba.order_microservice.domain.model.Order
import  com.rsba.order_microservice.aspect.AdminSecured
import com.rsba.order_microservice.context.token.ITokenImpl
import com.rsba.order_microservice.domain.model.Item
import com.rsba.order_microservice.domain.model.ProgressionStep
import  com.rsba.order_microservice.repository.OrderRepository
import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.relay.*
import org.springframework.stereotype.Component

import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import java.util.*


@Component
class OrderQueryResolver(private val service: OrderRepository, private val logger: KLogger) : GraphQLQueryResolver,
    ITokenImpl, GenericRetrieveConnection(myLogger = logger) {

    @AdminSecured
    suspend fun retrieveAllOrder(
        first: Int,
        after: UUID? = null,
        env: DataFetchingEnvironment
    ): Connection<Order>? = retrieveFn(
        entry = service.onRetrieveAllOrder(token = readToken(environment = env), first = first, after = after),
        first = first,
        after = after
    )

    @AdminSecured
    suspend fun retrieveOneOrder(id: UUID, env: DataFetchingEnvironment): Optional<Order> {
        logger.warn { "+OrderQueryResolver->retrieveOneOrder" }
        return service.retrieveOneOrder(id = id, token = readToken(environment = env))
    }

    @AdminSecured
    suspend fun retrieveItemInOrderById(itemId: UUID, orderId: UUID, env: DataFetchingEnvironment): Optional<Item> {
        logger.warn { "+OrderQueryResolver->retrieveItemInOrderById" }
        return service.retrieveItemInOrderById(
            itemId = itemId,
            orderId = orderId,
            token = readToken(environment = env)
        )
    }

    @AdminSecured
    suspend fun retrieveProgressionStepsByOrderId(orderId: UUID, env: DataFetchingEnvironment): List<ProgressionStep> {
        logger.warn { "+OrderQueryResolver->retrieveProgressionStepsByOrderId" }
        return service.retrieveProgressionStepsByOrderId(orderId = orderId, token = readToken(environment = env))
    }

    @AdminSecured
    suspend fun retrieveNumberOfActiveOrder(env: DataFetchingEnvironment): Optional<Int> {
        logger.warn { "+OrderQueryResolver->retrieveNumberOfActiveOrder" }
        return service.retrieveNumberOfActiveOrder(token = readToken(environment = env))
    }

    @AdminSecured
    suspend fun retrieveOrderByUserToken(
        first: Int,
        after: UUID? = null,
        env: DataFetchingEnvironment
    ): Connection<Order>? =
        retrieveFn(
            entry = service.orderByUserToken(token = readToken(environment = env), first = first, after = after),
            first = first,
            after = after
        )

    @AdminSecured
    suspend fun retrieveNextOrderReference(env: DataFetchingEnvironment): String {
        logger.warn { "+OrderQueryResolver->retrieveNextOrderReference" }
        return service.retrieveNextOrderReference(
            token = readToken(environment = env),
            companyId = UUID.randomUUID()
        )
    }

    @AdminSecured
    suspend fun retrieveCompletedOrders(
        first: Int,
        after: UUID? = null,
        env: DataFetchingEnvironment
    ): Connection<Order>? = retrieveFn(
        entry = service.myCompletedOrders(token = readToken(environment = env), first = first, after = after),
        first = first,
        after = after
    )

    @AdminSecured
    suspend fun retrieveOrdersByDepartmentId(
        departmentId: UUID,
        first: Int,
        after: UUID? = null,
        env: DataFetchingEnvironment
    ): Connection<Order>? = retrieveFn(
        entry = service.myOrdersByDepartmentId(
            departmentId = departmentId,
            token = readToken(environment = env),
            first = first,
            after = after
        ), first = first, after = after
    )

}