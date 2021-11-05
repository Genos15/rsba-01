package com.rsba.component_microservice.domain.repository

import com.rsba.component_microservice.domain.input.*
import com.rsba.component_microservice.domain.model.Group
import com.rsba.component_microservice.domain.model.Operation
import graphql.schema.DataFetchingEnvironment
import java.util.*

interface OperationRepository {

    suspend fun createOrEditOperation(input: CreateOrEditOperationInput, token: UUID): Optional<Operation>

    suspend fun deleteOperation(input: UUID, token: UUID): Int

    suspend fun retrieveAllOperation(first: Int, after: UUID?, token: UUID): MutableList<Operation>

    suspend fun attachOperationToGroup(input: OperationAndGroupInput, token: UUID): Optional<Operation>

    suspend fun detachOperationToGroup(input: OperationAndGroupInput, token: UUID): Optional<Operation>

    suspend fun retrieveGroupInOperation(
        ids: Set<UUID>,
        userId: UUID,
        page: Int,
        size: Int
    ): Map<UUID, MutableList<Group>>

    suspend fun importOperationFromJsonFile(environment: DataFetchingEnvironment): Optional<Boolean>

}