package com.rsba.usermicroservice.repository

import graphql.schema.DataFetchingEnvironment
import reactor.core.publisher.Mono
import java.util.*
import kotlin.jvm.Throws

interface PhotoRepository {

    @Throws(RuntimeException::class)
    fun addPhoto(environment: DataFetchingEnvironment): Mono<Optional<UUID>>

    @Throws(RuntimeException::class)
    suspend fun edit(environment: DataFetchingEnvironment): Mono<Optional<UUID>>

    @Throws(RuntimeException::class)
    suspend fun delete(input: UUID, token: UUID): Boolean
}