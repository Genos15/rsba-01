package  com.rsba.order_microservice.resolver.mutation

import com.rsba.order_microservice.domain.input.*
import com.rsba.order_microservice.domain.model.Order
import com.rsba.order_microservice.aspect.AdminSecured
import com.rsba.order_microservice.context.token.TokenImpl
import com.rsba.order_microservice.repository.OrderRepository
import graphql.kickstart.tools.GraphQLMutationResolver
import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import org.springframework.stereotype.Component
import java.util.*

@Component
class OrderMutation(
    private val service: OrderRepository,
    private val logger: KLogger,
    private val tokenImpl: TokenImpl
) : GraphQLMutationResolver {

    @AdminSecured
    suspend fun createOrder(
        input: CreateOrderInput,
        environment: DataFetchingEnvironment
    ): Optional<Order> {
        logger.warn { "+OrderMutation -> createOrder" }
        return service.createOrder(input = input, token = tokenImpl.read(environment = environment))
    }

    @AdminSecured
    suspend fun deleteOrder(input: UUID, environment: DataFetchingEnvironment): Int {
        logger.warn { "+OrderMutation -> deleteOrder" }
        return service.deleteOrder(input = input, token = tokenImpl.read(environment = environment))
    }

    @AdminSecured
    suspend fun editOrder(
        input: EditOrderInput,
        environment: DataFetchingEnvironment
    ): Optional<Order> {
        logger.warn { "+OrderMutation -> editOrder" }
        return service.editOrder(input = input, token = tokenImpl.read(environment = environment))
    }

    @AdminSecured
    suspend fun addCategoriesInOrder(
        input: AttachCategoryWithOrderInput,
        environment: DataFetchingEnvironment
    ): Optional<Order> {
        logger.warn { "+OrderMutation -> addCategoriesInOrder" }
        return service.addCategoriesInOrder(input = input, token = tokenImpl.read(environment = environment))
    }

    @AdminSecured
    suspend fun editCategoryOfOrder(
        input: EditCategoryOfOrderInput,
        environment: DataFetchingEnvironment
    ): Optional<Order> {
        logger.warn { "+OrderMutation -> editCategoryOfOrder" }
        return service.editCategoryOfOrder(input = input, token = tokenImpl.read(environment = environment))
    }

    @AdminSecured
    suspend fun addItemsInOrder(
        input: ItemInOrder,
        environment: DataFetchingEnvironment
    ): Optional<Order> {
        logger.warn { "+OrderMutation -> addItemsInOrder" }
        return service.addItemsInOrder(input = input, token = tokenImpl.read(environment = environment))
    }

    @AdminSecured
    suspend fun deleteItemsInOrder(
        input: ItemInOrder,
        environment: DataFetchingEnvironment
    ): Optional<Order> {
        logger.warn { "+OrderMutation -> deleteItemsInOrder" }
        return service.deleteItemsInOrder(input = input, token = tokenImpl.read(environment = environment))
    }

    @AdminSecured
    suspend fun terminateAnalysis(
        input: UUID,
        environment: DataFetchingEnvironment
    ): Optional<Order> {
        logger.warn { "+OrderMutation -> terminateAnalysis" }
        return service.terminateAnalysis(id = input, token = tokenImpl.read(environment = environment))
    }

    @AdminSecured
    suspend fun editItemInOrder(
        input: EditItemInOrderInput,
        environment: DataFetchingEnvironment
    ): Optional<Order> {
        logger.warn { "+OrderMutation->editItemInOrder" }
        return service.editItemInOrder(input = input, token = tokenImpl.read(environment = environment))
    }

    suspend fun importOrderFromJsonFile(environment: DataFetchingEnvironment): Optional<Boolean> {
        return service.importOrderFromJsonFile(environment = environment)
    }
}