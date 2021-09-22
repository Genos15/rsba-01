package com.rsba.file_microservice.repository

import graphql.schema.DataFetchingEnvironment
import org.springframework.data.mongodb.gridfs.ReactiveGridFsResource
import reactor.core.publisher.Mono
import java.util.*

interface PhotoRepository {

    suspend fun addPhoto(environment: DataFetchingEnvironment): UUID?

    suspend fun removePhoto(id: UUID): Boolean

    fun retrievePhoto(id: UUID): Mono<ReactiveGridFsResource>

}