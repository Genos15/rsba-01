package  com.rsba.order_microservice.resolver.query

import com.rsba.order_microservice.domain.model.*
import com.rsba.order_microservice.domain.repository.ItemRepository
import com.rsba.order_microservice.domain.security.TokenAnalyzer
import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.relay.Connection
import org.springframework.stereotype.Component

import graphql.schema.DataFetchingEnvironment
import java.util.*


@Component
class ItemQueryResolver(private val service: ItemRepository, private val deduct: TokenAnalyzer) : GraphQLQueryResolver,
    GenericRetrieveConnection {

    suspend fun buildItemElk(
        from: UUID?,
        orderId: UUID?,
        height: Int,
        width: Int,
        environment: DataFetchingEnvironment,
    ): ElkGraph<ElkGraphItemNode> =
        service.elk(
            token = deduct(environment = environment),
            from = from,
            height = height,
            width = width,
            orderId = orderId
        )

    suspend fun findItemStatistics(id: UUID, environment: DataFetchingEnvironment): Optional<ItemStatistics> = perform(
        entries = service.statistics(ids = setOf(id), token = deduct(environment = environment)),
        id = id
    )

    suspend fun findItemWhoAdded(id: UUID, environment: DataFetchingEnvironment): Optional<User> = perform(
        entries = service.whoAdded(ids = setOf(id), token = deduct(environment = environment)),
        id = id
    )

    suspend fun retrieveItemTechnologies(
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

    suspend fun retrieveItemParameters(
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

}