package com.rsba.component_microservice.domain.repository

import com.rsba.component_microservice.domain.input.*
import com.rsba.component_microservice.domain.model.MutationAction
import com.rsba.component_microservice.domain.model.Operation
import com.rsba.component_microservice.domain.model.Technology
import java.util.*

interface TechnologyRepository {

    suspend fun toCreateOrEdit(
        input: TechnologyInput,
        action: MutationAction? = null,
        token: UUID
    ): Optional<Technology>

    suspend fun toDelete(input: UUID, token: UUID): Boolean

    suspend fun find(id: UUID, token: UUID): Optional<Technology>

    suspend fun retrieve(first: Int, after: UUID?, token: UUID): List<Technology>

    suspend fun search(input: String, first: Int, after: UUID?, token: UUID): List<Technology>

    suspend fun count(token: UUID): Int

    suspend fun operations(
        ids: Set<UUID>,
        first: Int = 1000,
        after: UUID? = null,
        token: UUID = UUID.randomUUID()
    ): Map<UUID, List<Operation>>

}