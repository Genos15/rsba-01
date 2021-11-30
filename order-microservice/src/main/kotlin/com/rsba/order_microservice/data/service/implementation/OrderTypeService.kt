package com.rsba.order_microservice.data.service.implementation

import com.rsba.order_microservice.domain.input.OrderTypeInput
import com.rsba.order_microservice.domain.model.MutationAction
import com.rsba.order_microservice.domain.model.OrderType
import com.rsba.order_microservice.domain.repository.OrderTypeRepository
import com.rsba.order_microservice.domain.usecase.common.*
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import java.util.*

@Service
class OrderTypeService(
    private val database: DatabaseClient,
    @Qualifier("create_edit_order_type") private val createOrEditUseCase: CreateOrEditUseCase<OrderTypeInput, OrderType>,
    @Qualifier("delete_order_type") private val deleteUseCase: DeleteUseCase<OrderType>,
    @Qualifier("find_order_type") private val findUseCase: FindUseCase<OrderType>,
    @Qualifier("retrieve_order_types") private val retrieveUseCase: RetrieveUseCase<OrderType>,
    @Qualifier("search_order_types") private val searchUseCase: SearchUseCase<OrderType>,
    @Qualifier("count_order_types") private val countUseCase: CountUseCase,
) : OrderTypeRepository {

    override suspend fun toCreateOrEdit(
        input: OrderTypeInput,
        action: MutationAction?,
        token: UUID
    ): Optional<OrderType> = createOrEditUseCase(database = database, input = input, action = action, token = token)

    override suspend fun toDelete(input: UUID, token: UUID): Boolean =
        deleteUseCase(database = database, input = input, token = token)

    override suspend fun find(id: UUID, token: UUID): Optional<OrderType> =
        findUseCase(database = database, id = id, token = token)

    override suspend fun retrieve(first: Int, after: UUID?, token: UUID): List<OrderType> =
        retrieveUseCase(database = database, first = first, after = after, token = token)

    override suspend fun search(input: String, first: Int, after: UUID?, token: UUID): List<OrderType> =
        searchUseCase(database = database, first = first, after = after, token = token, input = input)

    override suspend fun count(token: UUID): Int =
        countUseCase(database = database, token = token)

}