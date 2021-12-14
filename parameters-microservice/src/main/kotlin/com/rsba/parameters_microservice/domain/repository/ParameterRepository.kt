package com.rsba.parameters_microservice.domain.repository

import com.rsba.parameters_microservice.domain.input.*
import com.rsba.parameters_microservice.domain.model.MutationAction
import com.rsba.parameters_microservice.domain.model.Parameter
import java.util.*

interface ParameterRepository {

    suspend fun toCreateOrEdit(
        input: ParameterInput,
        action: MutationAction? = null,
        token: UUID
    ): Optional<Parameter>

    suspend fun toDelete(input: UUID, token: UUID): Boolean

    suspend fun find(id: UUID, token: UUID): Optional<Parameter>

    suspend fun retrieve(first: Int, after: UUID?, token: UUID): List<Parameter>

    suspend fun search(input: String, first: Int, after: UUID?, token: UUID): List<Parameter>

    suspend fun count(token: UUID): Int

}