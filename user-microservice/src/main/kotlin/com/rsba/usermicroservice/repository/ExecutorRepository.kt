package com.rsba.usermicroservice.repository

import com.rsba.usermicroservice.domain.input.ExecutorInput
import com.rsba.usermicroservice.domain.model.Executor
import java.util.*

interface ExecutorRepository {
    suspend fun createOrEdit(input: ExecutorInput, token: UUID): Optional<Executor>
    suspend fun renewPin(input: ExecutorInput, token: UUID): Optional<Executor>
    suspend fun delete(input: UUID, token: UUID): Boolean
    suspend fun retrieve(first: Int, after: UUID?, token: UUID): List<Executor>
    suspend fun search(input: String, first: Int, after: UUID?, token: UUID): List<Executor>
    suspend fun retrieveById(id: UUID, token: UUID): Optional<Executor>
}