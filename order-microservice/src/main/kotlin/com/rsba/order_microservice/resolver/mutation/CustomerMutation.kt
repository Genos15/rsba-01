package  com.rsba.order_microservice.resolver.mutation

import   com.rsba.order_microservice.domain.input.*
import  com.rsba.order_microservice.domain.model.Customer
import   com.rsba.order_microservice.aspect.AdminSecured
import com.rsba.order_microservice.context.token.TokenImpl
import  com.rsba.order_microservice.repository.CustomerRepository
import graphql.kickstart.tools.GraphQLMutationResolver
import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import org.springframework.stereotype.Component
import java.util.*

@Component
class CustomerMutation(
    private val service: CustomerRepository,
    private val tokenImpl: TokenImpl,
    private val logger: KLogger
) :
    GraphQLMutationResolver {

    @AdminSecured
    suspend fun createOrEditCustomer(
        input: CreateOrEditCustomerInput,
        environment: DataFetchingEnvironment
    ): Optional<Customer> {
        logger.warn { "+-- CustomerMutation -> createOrEditCustomer" }
        return service.createOrEditCustomer(input = input, token = tokenImpl.read(environment = environment))
    }

    @AdminSecured
    suspend fun addEntityToCustomer(
        input: AddEntityToCustomerInput,
        environment: DataFetchingEnvironment
    ): Optional<Customer> {
        logger.warn { "+-- CustomerMutation -> addEntityToCustomer" }
        return service.addEntityToCustomer(input = input, token = tokenImpl.read(environment = environment))
    }

    @AdminSecured
    suspend fun removeEntityOfCustomer(
        input: RemoveEntityOfCustomerInput,
        environment: DataFetchingEnvironment
    ): Optional<Customer> {
        logger.warn { "+-- CustomerMutation -> removeEntityOfCustomer" }
        return service.removeEntityOfCustomer(input = input, token = tokenImpl.read(environment = environment))
    }

    @AdminSecured
    suspend fun deleteCustomer(input: UUID, environment: DataFetchingEnvironment): Int {
        logger.warn { "+-- CustomerMutation -> deleteCustomer" }
        return service.deleteCustomer(input = input, token = tokenImpl.read(environment = environment))
    }

}