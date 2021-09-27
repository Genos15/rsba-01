package com.rsba.usermicroservice.repository

import graphql.schema.DataFetchingEnvironment
import java.util.*
import kotlin.jvm.Throws

interface PhotoRepository {

    @Throws(RuntimeException::class)
    suspend fun add(environment: DataFetchingEnvironment): Optional<UUID>

    @Throws(RuntimeException::class)
    suspend fun edit(environment: DataFetchingEnvironment): Optional<UUID>

    @Throws(RuntimeException::class)
    suspend fun delete(input: UUID, token: UUID): Boolean
}