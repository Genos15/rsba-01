package  com.rsba.order_microservice.resolver.query

import  com.rsba.order_microservice.aspect.AdminSecured
import com.rsba.order_microservice.data.context.token.TokenImpl
import com.rsba.order_microservice.domain.model.DetailItemInOrder
import com.rsba.order_microservice.domain.repository.ItemRepository
import graphql.kickstart.tools.GraphQLQueryResolver
import org.springframework.stereotype.Component

import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import java.util.*


@Component
class ItemQueryResolver(
    private val service: ItemRepository,
    private val logger: KLogger,
    private val tokenImpl: TokenImpl
) : GraphQLQueryResolver {

    @AdminSecured
    suspend fun retrieveDetailOfItemInOrder(
        orderId: UUID,
        itemId: UUID,
        env: DataFetchingEnvironment
    ): Optional<DetailItemInOrder> {
        logger.warn { "+ItemQueryResolver->retrieveDetailOfItemInOrder" }
        return service.myDetails(
            token = tokenImpl.read(environment = env),
            orderId = orderId,
            itemId = itemId,
        )
    }

}