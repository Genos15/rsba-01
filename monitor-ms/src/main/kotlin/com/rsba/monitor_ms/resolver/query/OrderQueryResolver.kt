package  com.rsba.monitor_ms.resolver.query

import  com.rsba.monitor_ms.configuration.request_helper.CursorUtil
import  com.rsba.monitor_ms.domain.model.Order
import  com.rsba.monitor_ms.aspect.AdminSecured
import com.rsba.monitor_ms.context.token.TokenImpl
import  com.rsba.monitor_ms.repository.OrderRepository
import graphql.kickstart.tools.GraphQLQueryResolver
import org.springframework.stereotype.Component

import graphql.schema.DataFetchingEnvironment
import mu.KLogger


@Component
class OrderQueryResolver(
    private val cursorUtil: CursorUtil,
    private val service: OrderRepository,
    private val logger: KLogger,
    private val tokenImpl: TokenImpl
) : GraphQLQueryResolver {

    @AdminSecured
    suspend fun all(environment: DataFetchingEnvironment): List<Order> {
        return listOf()
    }

}