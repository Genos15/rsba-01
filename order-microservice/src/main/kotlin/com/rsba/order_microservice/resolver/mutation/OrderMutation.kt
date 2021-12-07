package  com.rsba.order_microservice.resolver.mutation

import com.rsba.order_microservice.domain.input.*
import com.rsba.order_microservice.domain.model.Order
import com.rsba.order_microservice.aspect.AdminSecured
import com.rsba.order_microservice.domain.model.Item
import com.rsba.order_microservice.domain.model.MutationAction
import com.rsba.order_microservice.domain.model.Task
import com.rsba.order_microservice.domain.repository.OrderRepository
import com.rsba.order_microservice.domain.security.TokenAnalyzer
import graphql.kickstart.tools.GraphQLMutationResolver
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import java.util.*

@Component
class OrderMutation(private val service: OrderRepository, private val deduct: TokenAnalyzer) : GraphQLMutationResolver {

    @AdminSecured
    suspend fun createOrEditOrder(
        input: OrderInput,
        action: MutationAction? = null,
        environment: DataFetchingEnvironment
    ): Optional<Order> =
        service.toCreateOrEdit(input = input, token = deduct(environment = environment), action = action)

    @AdminSecured
    suspend fun deleteOrder(input: UUID, environment: DataFetchingEnvironment): Boolean =
        service.toDelete(input = input, token = deduct(environment = environment))

    @AdminSecured
    suspend fun editTask(
        input: TaskInput,
        action: MutationAction? = null,
        environment: DataFetchingEnvironment
    ): Optional<Task> =
        service.toEditTask(input = input, token = deduct(environment = environment), action = action)

    @AdminSecured
    suspend fun editItem(
        input: ItemInput,
        environment: DataFetchingEnvironment
    ): Optional<Item> = service.toEditItem(input = input, token = deduct(environment = environment))
}