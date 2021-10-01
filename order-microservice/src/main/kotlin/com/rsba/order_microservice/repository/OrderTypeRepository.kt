package com.rsba.order_microservice.repository

import com.rsba.order_microservice.domain.input.OrderTypeInput
import com.rsba.order_microservice.domain.model.OrderType
import java.util.*

interface OrderTypeRepository {
    suspend fun createOrEdit(input: OrderTypeInput, token: UUID): Optional<OrderType>
    suspend fun delete(input: UUID, token: UUID): Boolean
    suspend fun retrieve(first: Int, after: UUID?, token: UUID): List<OrderType>
    suspend fun search(input: String, first: Int, after: UUID?, token: UUID): List<OrderType>
    suspend fun retrieveById(id: UUID, token: UUID): Optional<OrderType>
}