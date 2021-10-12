package com.rsba.usermicroservice.repository

import graphql.schema.DataFetchingEnvironment
import org.springframework.data.mongodb.gridfs.ReactiveGridFsResource
import javax.servlet.http.Part
import reactor.core.publisher.Mono
import java.util.*
import kotlin.jvm.Throws

interface PhotoRepository {

    @Throws(RuntimeException::class)
    fun addPhoto(environment: DataFetchingEnvironment): Mono<Optional<UUID>>

    @Throws(RuntimeException::class)
    suspend fun edit(part: Part, environment: DataFetchingEnvironment): Mono<Optional<UUID>>

    @Throws(RuntimeException::class)
    fun delete(input: UUID, token: UUID): Mono<Boolean>

    fun retrievePhoto(id: UUID): Mono<ReactiveGridFsResource>
}