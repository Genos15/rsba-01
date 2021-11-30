package com.rsba.order_microservice.resolver.mutation

import com.rsba.order_microservice.aspect.AdminSecured
import com.rsba.order_microservice.domain.input.OrderTypeInput
import com.rsba.order_microservice.domain.model.MutationAction
import com.rsba.order_microservice.domain.model.OrderType
import com.rsba.order_microservice.domain.repository.OrderTypeRepository
import com.rsba.order_microservice.domain.security.TokenAnalyzer
import graphql.kickstart.tools.GraphQLMutationResolver
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import java.util.*

@Component
class OrderTypeMutation(private val service: OrderTypeRepository, private val deduct: TokenAnalyzer) :
    GraphQLMutationResolver {

    @AdminSecured
    suspend fun createOrEditOrderType(
        input: OrderTypeInput,
        action: MutationAction? = null,
        environment: DataFetchingEnvironment
    ): Optional<OrderType> =
        service.toCreateOrEdit(input = input, token = deduct(environment = environment), action = action)

    @AdminSecured
    suspend fun deleteOrderType(input: UUID, environment: DataFetchingEnvironment): Boolean =
        service.toDelete(input = input, token = deduct(environment = environment))
}
