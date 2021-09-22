package  com.rsba.monitor_ms.repository

import  com.rsba.monitor_ms.domain.input.*
import  com.rsba.monitor_ms.domain.model.Customer
import graphql.schema.DataFetchingEnvironment
import java.util.*

interface CustomerRepository {

    suspend fun createOrEditCustomer(input: CreateOrEditCustomerInput, token: UUID): Optional<Customer>

    suspend fun addEntityToCustomer(input: AddEntityToCustomerInput, token: UUID): Optional<Customer>

    suspend fun removeEntityOfCustomer(input: RemoveEntityOfCustomerInput, token: UUID): Optional<Customer>

    suspend fun deleteCustomer(input: UUID, token: UUID): Int

    suspend fun retrieveAllCustomer(token: UUID): MutableList<Customer>

    suspend fun retrieveCustomerOfOrder(
        ids: Set<UUID>,
        userId: UUID,
        page: Int,
        size: Int
    ): Map<UUID, Customer>

    suspend fun retrieveEntitiesOfCustomer(
        ids: Set<UUID>,
        userId: UUID,
        page: Int,
        size: Int
    ): Map<UUID, MutableList<Customer>>

    suspend fun retrieveOneCustomer(id: UUID, token: UUID): Optional<Customer>

}