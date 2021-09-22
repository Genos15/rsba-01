package  com.rsba.order_microservice.resolver.query

import  com.rsba.order_microservice.configuration.request_helper.CursorUtil
import  com.rsba.order_microservice.domain.model.Customer
import  com.rsba.order_microservice.aspect.AdminSecured
import com.rsba.order_microservice.context.token.TokenImpl
import  com.rsba.order_microservice.repository.CustomerRepository
import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.relay.*
import org.springframework.stereotype.Component
import java.util.*

import graphql.schema.DataFetchingEnvironment
import mu.KLogger


@Component
class CustomerQueryResolver(
    private val cursorUtil: CursorUtil,
    private val service: CustomerRepository,
    private val logger: KLogger,
    private val tokenImpl: TokenImpl
) : GraphQLQueryResolver {

    @AdminSecured
    suspend fun retrieveAllCustomer(
        first: Int,
        after: UUID? = null,
        env: DataFetchingEnvironment
    ): Connection<Customer>? {

        logger.warn { "+----- CustomerQueryResolver -> retrieveAllCustomer" }

        val edges: List<Edge<Customer>> =
            service.retrieveAllCustomer(token = tokenImpl.read(environment = env), first = first, after = after).map {
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
    suspend fun retrieveOneCustomer(id: UUID, env: DataFetchingEnvironment): Optional<Customer> {
        logger.warn { "+----- CustomerQueryResolver -> retrieveOneOrder" }
        return service.retrieveOneCustomer(id = id, token = tokenImpl.read(environment = env))
    }
}