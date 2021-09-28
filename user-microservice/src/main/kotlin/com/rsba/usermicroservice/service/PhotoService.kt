package com.rsba.usermicroservice.service

import com.rsba.usermicroservice.repository.PhotoRepository
import com.rsba.usermicroservice.service.implementation.AddPhotoImpl
import graphql.schema.DataFetchingEnvironment
import org.springframework.data.mongodb.gridfs.ReactiveGridFsOperations
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.*

@Service
class PhotoService(private val database: ReactiveGridFsOperations) : PhotoRepository, AddPhotoImpl {
    /**
     * @param environment the data wrapper GraphQL engine uses to keep request meta data.
     *
     * @return {@link Optional<UUID>} url id of a saved file
     */
    override fun addPhoto(environment: DataFetchingEnvironment): Mono<Optional<UUID>> =
        performAddPhoto(database = database, environment = environment)

    override suspend fun edit(environment: DataFetchingEnvironment): Mono<Optional<UUID>> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(input: UUID, token: UUID): Boolean {
        TODO("Not yet implemented")
    }

}