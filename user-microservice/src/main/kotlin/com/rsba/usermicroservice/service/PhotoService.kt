package com.rsba.usermicroservice.service

import com.rsba.usermicroservice.repository.PhotoRepository
import com.rsba.usermicroservice.service.implementation.files.AddPhotoImpl
import com.rsba.usermicroservice.service.implementation.files.RetrieveFileImpl
import graphql.schema.DataFetchingEnvironment
import org.springframework.data.mongodb.gridfs.ReactiveGridFsOperations
import org.springframework.data.mongodb.gridfs.ReactiveGridFsResource
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.*

@Service
class PhotoService(private val database: ReactiveGridFsOperations) : PhotoRepository, AddPhotoImpl, RetrieveFileImpl {
    /**
     * @param environment the data wrapper GraphQL engine uses to keep request metadata
     * @return {@link Optional<UUID>} url id of a saved file
     */
    override fun addPhoto(environment: DataFetchingEnvironment): Mono<Optional<UUID>> =
        addPhotoFn(database = database, environment = environment)

    override suspend fun edit(environment: DataFetchingEnvironment): Mono<Optional<UUID>> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(input: UUID, token: UUID): Boolean {
        TODO("Not yet implemented")
    }

    /**
     * @param id the unique reference of the photo file
     * @return {@link Mono<ReactiveGridFsResource>}
     */
    override fun retrievePhoto(id: UUID): Mono<ReactiveGridFsResource> = retrieveFileFn(id = id, database = database)

}