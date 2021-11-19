package  com.rsba.order_microservice.data.service.implementation

import  com.rsba.order_microservice.domain.input.*
import  com.rsba.order_microservice.domain.model.Customer
import com.rsba.order_microservice.domain.model.MutationAction
import  com.rsba.order_microservice.domain.repository.CustomerRepository
import com.rsba.order_microservice.domain.usecase.common.*
import com.rsba.order_microservice.domain.usecase.custom.customer.RetrieveCustomerEntitiesUseCase
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomerService(
    private val database: DatabaseClient,
    @Qualifier("create_edit_customer") private val createOrEditUseCase: CreateOrEditUseCase<CustomerInput, Customer>,
    @Qualifier("delete_customer") private val deleteUseCase: DeleteUseCase<Customer>,
    @Qualifier("find_customer") private val findUseCase: FindUseCase<Customer>,
    @Qualifier("retrieve_customer") private val retrieveUseCase: RetrieveUseCase<Customer>,
    @Qualifier("search_customer") private val searchUseCase: SearchUseCase<Customer>,
    @Qualifier("count_customer") private val countUseCase: CountUseCase,
    private val entitiesUseCase: RetrieveCustomerEntitiesUseCase
) : CustomerRepository {

    override suspend fun toCreateOrEdit(
        input: CustomerInput,
        action: MutationAction?,
        token: UUID
    ): Optional<Customer> = createOrEditUseCase(database = database, input = input, action = action, token = token)

    override suspend fun toDelete(input: UUID, token: UUID): Boolean =
        deleteUseCase(database = database, input = input, token = token)

    override suspend fun find(id: UUID, token: UUID): Optional<Customer> =
        findUseCase(database = database, id = id, token = token)

    override suspend fun retrieve(first: Int, after: UUID?, token: UUID): List<Customer> =
        retrieveUseCase(database = database, first = first, after = after, token = token)

    override suspend fun search(input: String, first: Int, after: UUID?, token: UUID): List<Customer> =
        searchUseCase(database = database, first = first, after = after, token = token, input = input)

    override suspend fun count(token: UUID): Int =
        countUseCase(database = database, token = token)

    override suspend fun entities(ids: Set<UUID>, first: Int, after: UUID?, token: UUID): Map<UUID, List<Customer>> =
        entitiesUseCase(database = database, ids = ids, token = token, first = first, after = after)

}
