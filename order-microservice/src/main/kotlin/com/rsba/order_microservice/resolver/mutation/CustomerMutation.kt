package  com.rsba.order_microservice.resolver.mutation

import   com.rsba.order_microservice.domain.input.*
import   com.rsba.order_microservice.aspect.AdminSecured
import com.rsba.order_microservice.domain.model.Customer
import com.rsba.order_microservice.domain.model.MutationAction
import  com.rsba.order_microservice.domain.repository.CustomerRepository
import com.rsba.order_microservice.domain.security.TokenAnalyzer
import graphql.kickstart.tools.GraphQLMutationResolver
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import java.util.*

@Component
class CustomerMutation(private val service: CustomerRepository, private val deduct: TokenAnalyzer) :
    GraphQLMutationResolver {

    @AdminSecured
    suspend fun createOrEditCustomer(
        input: CustomerInput,
        action: MutationAction? = null,
        environment: DataFetchingEnvironment
    ): Optional<Customer> =
        service.toCreateOrEdit(input = input, token = deduct(environment = environment), action = action)

    @AdminSecured
    suspend fun deleteCustomer(input: UUID, environment: DataFetchingEnvironment): Boolean =
        service.toDelete(input = input, token = deduct(environment = environment))

}