package com.rsba.order_microservice.repository

import com.rsba.order_microservice.domain.input.ParameterInput
import com.rsba.order_microservice.domain.model.Parameter
import java.util.*

interface ParameterRepository {

    suspend fun createOrEdit(input: ParameterInput, token: UUID): Optional<Parameter>
    suspend fun addOrRemovePotentialValue(input: ParameterInput, token: UUID): Optional<Parameter>
    suspend fun delete(input: UUID, token: UUID): Boolean
    suspend fun retrieve(first: Int, after: UUID?, token: UUID): List<Parameter>
    suspend fun search(input: String, first: Int, after: UUID?, token: UUID): List<Parameter>
    suspend fun retrieveByTaskId(taskId: UUID, token: UUID): List<Parameter>
    suspend fun retrieveByItemId(itemId: UUID, token: UUID): List<Parameter>

}