package com.rsba.order_microservice.domain.repository

import com.rsba.order_microservice.domain.input.FindItemAttachedToTechnologyInput
import com.rsba.order_microservice.domain.input.FindOperationAttachedToTechnologyInput
import com.rsba.order_microservice.domain.input.FindTaskAttachedToTechnologyInput
import com.rsba.order_microservice.domain.model.Item
import com.rsba.order_microservice.domain.model.Operation
import com.rsba.order_microservice.domain.model.Task
import java.util.*

interface TechnologyRepository {

    suspend fun myOperations(
        ids: Set<UUID>,
        userId: UUID,
        page: Int,
        size: Int
    ): Map<UUID, List<Operation>>

    suspend fun operationsAttachedTechnologyInOrder(
        input: FindOperationAttachedToTechnologyInput,
        token: UUID
    ): List<Operation>

    suspend fun tasksAttachedTechnologyInOrder(
        input: FindTaskAttachedToTechnologyInput,
        token: UUID
    ): List<Task>

    suspend fun itemsAttachedTechnologyInOrder(input: FindItemAttachedToTechnologyInput, token: UUID): List<Item>
}