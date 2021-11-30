package com.rsba.order_microservice.domain.repository

import com.rsba.order_microservice.domain.input.OrderTypeInput
import com.rsba.order_microservice.domain.model.MutationAction
import com.rsba.order_microservice.domain.model.OrderType
import java.util.*

interface OrderTypeRepository {

    suspend fun toCreateOrEdit(
        input: OrderTypeInput,
        action: MutationAction? = null,
        token: UUID
    ): Optional<OrderType>

    suspend fun toDelete(input: UUID, token: UUID): Boolean

    suspend fun find(id: UUID, token: UUID): Optional<OrderType>

    suspend fun retrieve(first: Int, after: UUID?, token: UUID): List<OrderType>

    suspend fun search(input: String, first: Int, after: UUID?, token: UUID): List<OrderType>

    suspend fun count(token: UUID): Int
}