package  com.rsba.order_microservice.resolver.query

import  com.rsba.order_microservice.aspect.AdminSecured
import com.rsba.order_microservice.domain.model.*
import  com.rsba.order_microservice.domain.repository.OrderRepository
import com.rsba.order_microservice.domain.security.TokenAnalyzer
import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.relay.*
import org.springframework.stereotype.Component

import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import java.util.*


@Component
class OrderQueryResolver(
    private val service: OrderRepository,
    private val logger: KLogger,
    private val deduct: TokenAnalyzer
) : GraphQLQueryResolver, GenericRetrieveConnection(myLogger = logger) {

    @AdminSecured
    suspend fun retrieveAllOrder(
        first: Int,
        after: UUID? = null,
        env: DataFetchingEnvironment
    ): Connection<Order>? = retrieveFn(
        entry = service.onRetrieveAllOrder(token = deduct(environment = env), first = first, after = after),
        first = first,
        after = after
    )

    @AdminSecured
    suspend fun retrieveOneOrder(id: UUID, env: DataFetchingEnvironment): Optional<Order> {
        logger.warn { "+OrderQueryResolver->retrieveOneOrder" }
        return service.retrieveOneOrder(id = id, token = deduct(environment = env))
    }

    @AdminSecured
    suspend fun retrieveItemInOrderById(itemId: UUID, orderId: UUID, env: DataFetchingEnvironment): Optional<Item> {
        logger.warn { "+OrderQueryResolver->retrieveItemInOrderById" }
        return service.retrieveItemInOrderById(
            itemId = itemId,
            orderId = orderId,
            token = deduct(environment = env)
        )
    }

    @AdminSecured
    suspend fun retrieveProgressionStepsByOrderId(orderId: UUID, env: DataFetchingEnvironment): List<ProgressionStep> {
        logger.warn { "+OrderQueryResolver->retrieveProgressionStepsByOrderId" }
        return service.retrieveProgressionStepsByOrderId(orderId = orderId, token = deduct(environment = env))
    }

    @AdminSecured
    suspend fun retrieveNumberOfActiveOrder(env: DataFetchingEnvironment): Optional<Int> {
        logger.warn { "+OrderQueryResolver->retrieveNumberOfActiveOrder" }
        return service.retrieveNumberOfActiveOrder(token = deduct(environment = env))
    }

    @AdminSecured
    suspend fun retrieveOrderByUserToken(
        first: Int,
        after: UUID? = null,
        level: OrderLevel?,
        env: DataFetchingEnvironment
    ): Connection<Order>? =
        retrieveFn(
            entry = service.orderByUserToken(
                token = deduct(environment = env),
                first = first,
                after = after,
                level = level
            ),
            first = first,
            after = after
        )

    @AdminSecured
    suspend fun retrieveNextOrderReference(env: DataFetchingEnvironment): String {
        logger.warn { "+OrderQueryResolver->retrieveNextOrderReference" }
        return service.retrieveNextOrderReference(
            token = deduct(environment = env),
            companyId = UUID.randomUUID()
        )
    }

    @AdminSecured
    suspend fun retrieveCompletedOrders(
        first: Int,
        after: UUID? = null,
        env: DataFetchingEnvironment
    ): Connection<Order>? = retrieveFn(
        entry = service.myCompletedOrders(token = deduct(environment = env), first = first, after = after),
        first = first,
        after = after
    )

    @AdminSecured
    suspend fun retrieveOrdersByDepartmentId(
        departmentId: UUID,
        first: Int,
        after: UUID? = null,
        level: OrderLevel?,
        env: DataFetchingEnvironment
    ): Connection<Order>? = retrieveFn(
        entry = service.myOrdersByDepartmentId(
            departmentId = departmentId,
            token = deduct(environment = env),
            first = first,
            after = after,
            level = level
        ), first = first, after = after
    )


    @AdminSecured
    suspend fun retrieveOrderCompletionLineGraph(
        year: Int,
        environment: DataFetchingEnvironment
    ): Optional<OrderCompletionLine> =
        service.completionLineGraph(year = year, token = deduct(environment = environment))

}