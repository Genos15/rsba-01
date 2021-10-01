package com.rsba.order_microservice.resolver.mutation

import com.rsba.order_microservice.aspect.AdminSecured
import com.rsba.order_microservice.context.token.ITokenImpl
import com.rsba.order_microservice.domain.input.OrderTypeInput
import com.rsba.order_microservice.domain.model.OrderType
import com.rsba.order_microservice.repository.OrderTypeRepository
import graphql.kickstart.tools.GraphQLMutationResolver
import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import org.springframework.stereotype.Component
import java.util.*

@Component
class OrderTypeMutation(
    private val service: OrderTypeRepository,
    private val logger: KLogger,
) : GraphQLMutationResolver, ITokenImpl {

    @AdminSecured
    suspend fun createOrEditOrderType(
        input: OrderTypeInput,
        environment: DataFetchingEnvironment
    ): Optional<OrderType> {
        logger.warn { "+OrderTypeMutation->createOrEdit" }
        return service.createOrEdit(input = input, token = readToken(environment = environment))
    }

    @AdminSecured
    suspend fun deleteOrderType(input: UUID, environment: DataFetchingEnvironment): Boolean {
        logger.warn { "+OrderTypeMutation->delete" }
        return service.delete(input = input, token = readToken(environment = environment))
    }
}