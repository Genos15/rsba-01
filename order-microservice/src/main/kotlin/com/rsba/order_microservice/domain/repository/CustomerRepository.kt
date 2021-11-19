package  com.rsba.order_microservice.domain.repository

import  com.rsba.order_microservice.domain.input.*
import  com.rsba.order_microservice.domain.model.Customer
import com.rsba.order_microservice.domain.model.MutationAction
import java.util.*

interface CustomerRepository {

    suspend fun toCreateOrEdit(
        input: CustomerInput,
        action: MutationAction? = null,
        token: UUID
    ): Optional<Customer>

    suspend fun toDelete(input: UUID, token: UUID): Boolean

    suspend fun find(id: UUID, token: UUID): Optional<Customer>

    suspend fun retrieve(first: Int, after: UUID?, token: UUID): List<Customer>

    suspend fun search(input: String, first: Int, after: UUID?, token: UUID): List<Customer>

    suspend fun count(token: UUID): Int

    suspend fun entities(
        ids: Set<UUID>,
        first: Int = 1000,
        after: UUID? = null,
        token: UUID = UUID.randomUUID()
    ): Map<UUID, List<Customer>>

}