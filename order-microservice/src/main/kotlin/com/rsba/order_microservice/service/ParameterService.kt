package com.rsba.order_microservice.service

import com.rsba.order_microservice.domain.input.ParameterInput
import com.rsba.order_microservice.domain.model.Parameter
import com.rsba.order_microservice.repository.ParameterRepository
import com.rsba.order_microservice.service.implementation.parameters.EditParameterImpl
import com.rsba.order_microservice.service.implementation.parameters.RetrieveParameterImpl
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import java.util.*

@Service
class ParameterService(private val database: DatabaseClient) : ParameterRepository,
    EditParameterImpl, RetrieveParameterImpl {

    override suspend fun createOrEdit(input: ParameterInput, token: UUID): Optional<Parameter> =
        createOrEditParameterFn(database = database, input = input, token = token)

    override suspend fun addOrRemovePotentialValue(input: ParameterInput, token: UUID): Optional<Parameter> =
        addRemovePotentialValueToParameterFn(database = database, input = input, token = token)

    override suspend fun delete(input: UUID, token: UUID): Boolean =
        deleteParameterFn(database = database, input = input, token = token)

    override suspend fun retrieve(first: Int, after: UUID?, token: UUID): List<Parameter> =
        retrieveParametersFn(first = first, after = after, token = token, database = database)

    override suspend fun search(input: String, first: Int, after: UUID?, token: UUID): List<Parameter> =
        retrieveParametersFn(first = first, after = after, token = token, database = database, input = input)

    override suspend fun retrieveByTaskId(taskId: UUID, token: UUID): List<Parameter> =
        retrieveParametersByTaskId(taskId = taskId, token = token, database = database)

    override suspend fun retrieveByItemId(itemId: UUID, token: UUID): List<Parameter> =
        retrieveParametersByItemId(itemId = itemId, token = token, database = database)

}