package  com.rsba.monitor_ms.resolver.query

import  com.rsba.monitor_ms.configuration.request_helper.CursorUtil
import  com.rsba.monitor_ms.domain.model.Customer
import  com.rsba.monitor_ms.aspect.AdminSecured
import com.rsba.monitor_ms.context.token.TokenImpl
import  com.rsba.monitor_ms.repository.CustomerRepository
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
        after: String? = null,
        env: DataFetchingEnvironment
    ): Connection<Customer>? {

        logger.warn { "+----- CustomerQueryResolver -> retrieveAllCustomer" }

        val edges: List<Edge<Customer>> = service.retrieveAllCustomer(token = tokenImpl.read(environment = env)).map {
            return@map DefaultEdge(it, cursorUtil.createCursorWith(it.id))
        }.take(first)

        val pageInfo = DefaultPageInfo(
            cursorUtil.firstCursorFrom(edges),
            cursorUtil.lastCursorFrom(edges),
            after != null && after.isNotEmpty(),
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