package com.rsba.order_microservice.data.service.implementation

import com.rsba.order_microservice.domain.input.OrderTypeInput
import com.rsba.order_microservice.domain.model.OrderType
import com.rsba.order_microservice.domain.repository.OrderTypeRepository
import com.rsba.order_microservice.data.service.implementation.order_type.EditOrderTypeImpl
import com.rsba.order_microservice.data.service.implementation.order_type.RetrieveOrderTypeImpl
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import java.util.*

@Service
class OrderTypeService(private val database: DatabaseClient) : OrderTypeRepository,
    EditOrderTypeImpl, RetrieveOrderTypeImpl {

    /**
     * @param token the key allowing the request to proceed.
     * @param input main Args, type of order object to save.
     * @return {@link List<OrderType>}
     */
    override suspend fun createOrEdit(input: OrderTypeInput, token: UUID): Optional<OrderType> =
        createOrEditImpl(input = input, token = token, database = database)

    /**
     * @param token the key allowing the request to proceed.
     * @param input, unique reference of the type of order to delete.
     * @return {@link <Boolean> depending on the result of the request}
     */
    override suspend fun delete(input: UUID, token: UUID): Boolean =
        deleteImpl(database = database, input = input, token = token)

    /**
     * @param token the key allowing the request to proceed.
     * @param first number of type of order to retrieve.
     * @param after last feedback retrieve in the previous request.
     * @return {@link List<OrderType>}
     */
    override suspend fun retrieve(first: Int, after: UUID?, token: UUID): List<OrderType> =
        retrieveImpl(database = database, first = first, after = after, token = token)

    /**
     * @param token the key allowing the request to proceed.
     * @param first number of type of order to retrieve.
     * @param after last type of order retrieve in the previous request.
     * @param input the sequence of character to search in the datasource.
     * @return {@link List<OrderType>}
     */
    override suspend fun search(input: String, first: Int, after: UUID?, token: UUID): List<OrderType> =
        retrieveImpl(database = database, first = first, after = after, input = input, token = token)

    /**
     * @param token the key allowing the request to proceed.
     * @param id, unique reference of the type of order to get.
     * @return {@link List<OrderType>}
     */
    override suspend fun retrieveById(id: UUID, token: UUID): Optional<OrderType> =
        retrieveByIdImpl(id = id, token = token, database = database)
}