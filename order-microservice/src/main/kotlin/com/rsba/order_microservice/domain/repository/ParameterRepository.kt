package com.rsba.order_microservice.domain.repository

import com.rsba.order_microservice.domain.input.ParameterInput
import java.util.*
import com.rsba.order_microservice.domain.model.Parameter

interface ParameterRepository {

    suspend fun createOrEdit(input: ParameterInput, token: UUID): Optional<Parameter>
    suspend fun addOrRemovePotentialValue(input: ParameterInput, token: UUID): Optional<Parameter>
    suspend fun delete(input: UUID, token: UUID): Boolean
    suspend fun retrieve(first: Int, after: UUID?, token: UUID): List<Parameter>
    suspend fun search(input: String, first: Int, after: UUID?, token: UUID): List<Parameter>
    suspend fun retrieveByTaskId(taskId: UUID, token: UUID): List<Parameter>
    suspend fun retrieveByItemId(itemId: UUID, token: UUID): List<Parameter>

    suspend fun myPotentialValues(
        ids: Set<UUID>,
        userId: UUID,
    ): Map<UUID, List<String>>

    suspend fun retrieveById(id: UUID, token: UUID): Optional<Parameter>
}