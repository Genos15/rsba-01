package  com.rsba.order_microservice.resolver.query

import  com.rsba.order_microservice.configuration.request_helper.CursorUtil
import  com.rsba.order_microservice.domain.model.Order
import  com.rsba.order_microservice.aspect.AdminSecured
import com.rsba.order_microservice.context.token.TokenImpl
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
class OrderQueryResolver(
    private val cursorUtil: CursorUtil,
    private val service: OrderRepository,
    private val logger: KLogger,
    private val tokenImpl: TokenImpl
) : GraphQLQueryResolver {

    @AdminSecured
    suspend fun retrieveAllOrder(
        first: Int,
        after: UUID? = null,
        env: DataFetchingEnvironment
    ): Connection<Order>? {

        logger.warn { "+OrderQueryResolver->retrieveAllOrder" }

        val edges: List<Edge<Order>> =
            service.onRetrieveAllOrder(token = tokenImpl.read(environment = env), first = first, after = after).map {
                return@map DefaultEdge(it, cursorUtil.createCursorWith(it.id))
            }.take(first)

        val pageInfo = DefaultPageInfo(
            cursorUtil.firstCursorFrom(edges),
            cursorUtil.lastCursorFrom(edges),
            after != null,
            edges.size >= first
        )

        return DefaultConnection(edges, pageInfo)
    }

    @AdminSecured
    suspend fun retrieveOneOrder(id: UUID, env: DataFetchingEnvironment): Optional<Order> {
        logger.warn { "+OrderQueryResolver->retrieveOneOrder" }
        return service.retrieveOneOrder(id = id, token = tokenImpl.read(environment = env))
    }

    @AdminSecured
    suspend fun retrieveItemInOrderById(itemId: UUID, orderId: UUID, env: DataFetchingEnvironment): Optional<Item> {
        logger.warn { "+OrderQueryResolver->retrieveItemInOrderById" }
        return service.retrieveItemInOrderById(
            itemId = itemId,
            orderId = orderId,
            token = tokenImpl.read(environment = env)
        )
    }

    @AdminSecured
    suspend fun retrieveProgressionStepsByOrderId(orderId: UUID, env: DataFetchingEnvironment): List<ProgressionStep> {
        logger.warn { "+OrderQueryResolver->retrieveProgressionStepsByOrderId" }
        return service.retrieveProgressionStepsByOrderId(orderId = orderId, token = tokenImpl.read(environment = env))
    }

    @AdminSecured
    suspend fun retrieveNumberOfActiveOrder(env: DataFetchingEnvironment): Optional<Int> {
        logger.warn { "+OrderQueryResolver->retrieveNumberOfActiveOrder" }
        return service.retrieveNumberOfActiveOrder(token = tokenImpl.read(environment = env))
    }

    @AdminSecured
    suspend fun retrieveOrderByUserToken(
        first: Int,
        after: UUID? = null,
        env: DataFetchingEnvironment
    ): Connection<Order>? {
        logger.warn { "+OrderQueryResolver->retrieveOrderByUserToken" }
        val edges: List<Edge<Order>> =
            service.orderByUserToken(token = tokenImpl.read(environment = env), first = first, after = after).map {
                return@map DefaultEdge(it, cursorUtil.createCursorWith(it.id))
            }.take(first)
        val pageInfo = DefaultPageInfo(
            cursorUtil.firstCursorFrom(edges),
            cursorUtil.lastCursorFrom(edges),
            after != null,
            edges.size >= first
        )
        return DefaultConnection(edges, pageInfo)
    }

}