package com.rsba.component_microservice.domain.repository

import com.rsba.component_microservice.domain.input.*
import com.rsba.component_microservice.domain.model.*
import java.util.*

interface OperationRepository {

    suspend fun toCreateOrEdit(
        input: OperationInput,
        action: MutationAction? = null,
        case: OperationEdition? = null,
        token: UUID
    ): Optional<Operation>

    suspend fun toDelete(input: UUID, token: UUID): Boolean

    suspend fun find(id: UUID, token: UUID): Optional<Operation>

    suspend fun retrieve(first: Int, after: UUID?, token: UUID): List<Operation>

    suspend fun search(input: String, first: Int, after: UUID?, token: UUID): List<Operation>

    suspend fun count(token: UUID): Int

    suspend fun departments(
        ids: Set<UUID>,
        first: Int = 1000,
        after: UUID? = null,
        token: UUID = UUID.randomUUID()
    ): Map<UUID, List<Group>>

}