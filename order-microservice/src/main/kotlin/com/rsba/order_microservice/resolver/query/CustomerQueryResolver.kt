package  com.rsba.order_microservice.resolver.query

import com.rsba.order_microservice.aspect.AdminSecured
import com.rsba.order_microservice.domain.model.Customer
import  com.rsba.order_microservice.domain.repository.CustomerRepository
import com.rsba.order_microservice.domain.security.TokenAnalyzer
import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.relay.Connection
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import java.util.*

@Component
class CustomerQueryResolver(private val service: CustomerRepository, private val deduct: TokenAnalyzer) :
    GraphQLQueryResolver, GenericRetrieveConnection {

    @AdminSecured
    suspend fun retrieveCustomers(
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<Customer> = perform(
        entries = service.retrieve(
            first = first,
            after = after,
            token = deduct(environment = environment)
        ),
        first = first,
        after = after
    )

    @AdminSecured
    suspend fun searchCustomers(
        input: String,
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<Customer> = perform(
        entries = service.search(
            input = input,
            first = first,
            after = after,
            token = deduct(environment = environment)
        ),
        first = first,
        after = after
    )

    suspend fun findCustomer(id: UUID, environment: DataFetchingEnvironment): Optional<Customer> =
        service.find(id = id, token = deduct(environment = environment))

    suspend fun retrieveCustomerEntities(
        id: UUID,
        first: Int,
        after: UUID? = null,
        environment: DataFetchingEnvironment
    ): Connection<Customer> = perform(
        entries = service.entities(
            ids = setOf(id),
            token = deduct(environment = environment),
            first = first,
            after = after
        ),
        first = first,
        after = after,
        id = id
    )

    suspend fun countCustomers(environment: DataFetchingEnvironment): Int =
        service.count(token = deduct(environment = environment))
}