package  com.rsba.order_microservice.resolver.query

import  com.rsba.order_microservice.aspect.AdminSecured
import com.rsba.order_microservice.data.context.token.TokenImpl
import com.rsba.order_microservice.domain.input.FindItemAttachedToTechnologyInput
import com.rsba.order_microservice.domain.input.FindOperationAttachedToTechnologyInput
import com.rsba.order_microservice.domain.input.FindTaskAttachedToTechnologyInput
import com.rsba.order_microservice.domain.model.Item
import com.rsba.order_microservice.domain.model.Operation
import com.rsba.order_microservice.domain.model.Task
import com.rsba.order_microservice.domain.repository.TechnologyRepository
import graphql.kickstart.tools.GraphQLQueryResolver
import org.springframework.stereotype.Component

import graphql.schema.DataFetchingEnvironment
import mu.KLogger


@Component
class TechnologyQueryResolver(
    private val service: TechnologyRepository,
    private val logger: KLogger,
    private val tokenImpl: TokenImpl
) : GraphQLQueryResolver {

    @AdminSecured
    suspend fun operationsAttachedTechnologyInOrder(
        id: FindOperationAttachedToTechnologyInput,
        env: DataFetchingEnvironment
    ): List<Operation> {
        logger.warn { "+TechnologyQueryResolver->operationsAttachedTechnologyInOrder" }
        return service.operationsAttachedTechnologyInOrder(input = id, token = tokenImpl.read(environment = env))
    }

    @AdminSecured
    suspend fun tasksAttachedTechnologyInOrder(
        id: FindTaskAttachedToTechnologyInput,
        env: DataFetchingEnvironment
    ): List<Task> {
        logger.warn { "+TechnologyQueryResolver->tasksAttachedTechnologyInOrder" }
        return service.tasksAttachedTechnologyInOrder(input = id, token = tokenImpl.read(environment = env))
    }

    @AdminSecured
    suspend fun itemsAttachedTechnologyInOrder(
        id: FindItemAttachedToTechnologyInput,
        env: DataFetchingEnvironment
    ): List<Item> {
        logger.warn { "+TechnologyQueryResolver->itemsAttachedTechnologyInOrder" }
        return service.itemsAttachedTechnologyInOrder(input = id, token = tokenImpl.read(environment = env))
    }

}