package com.rsba.component_microservice.repository

import com.rsba.component_microservice.domain.input.*
import com.rsba.component_microservice.domain.model.Operation
import com.rsba.component_microservice.domain.model.Technology
import graphql.schema.DataFetchingEnvironment
import java.util.*

interface TechnologyRepository {
    suspend fun createOrEdit(input: CreateOrEditTechnologyInput, token: UUID): Optional<Technology>
    suspend fun delete(input: UUID, token: UUID): Boolean
    suspend fun retrieve(first: Int, after: UUID?, token: UUID): List<Technology>
    suspend fun retrieveById(id: UUID, token: UUID): Optional<Technology>
    suspend fun unpinOperation(input: List<TechnologyAndOperation>, token: UUID): Optional<Technology>
    suspend fun search(content: String, token: UUID): List<Technology>
    suspend fun myOperations(
        ids: Set<UUID>,
        userId: UUID,
        page: Int,
        size: Int
    ): Map<UUID, List<Operation>>

    suspend fun importTechnologyFromJsonFile(environment: DataFetchingEnvironment): Optional<Boolean>

}