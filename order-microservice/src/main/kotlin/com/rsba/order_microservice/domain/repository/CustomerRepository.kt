package  com.rsba.order_microservice.domain.repository

import  com.rsba.order_microservice.domain.input.*
import  com.rsba.order_microservice.domain.model.Customer
import java.util.*

interface CustomerRepository {

    suspend fun createOrEditCustomer(input: CreateOrEditCustomerInput, token: UUID): Optional<Customer>

    suspend fun addEntityToCustomer(input: AddEntityToCustomerInput, token: UUID): Optional<Customer>

    suspend fun removeEntityOfCustomer(input: RemoveEntityOfCustomerInput, token: UUID): Optional<Customer>

    suspend fun deleteCustomer(input: UUID, token: UUID): Int

    suspend fun retrieveAllCustomer(first: Int, after: UUID?, token: UUID): MutableList<Customer>

    suspend fun retrieveCustomerOfOrder(
        ids: Set<UUID>,
        userId: UUID,
        page: Int,
        size: Int
    ): Map<UUID, Customer?>

    suspend fun retrieveEntitiesOfCustomer(
        ids: Set<UUID>,
        userId: UUID,
        page: Int,
        size: Int
    ): Map<UUID, MutableList<Customer>>

    suspend fun retrieveOneCustomer(id: UUID, token: UUID): Optional<Customer>

}