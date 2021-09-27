package com.rsba.usermicroservice.service


import com.mongodb.BasicDBObject
import com.rsba.usermicroservice.context.CustomGraphQLContext
import com.rsba.usermicroservice.domain.model.FileType
import com.rsba.usermicroservice.repository.PhotoRepository
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.reactive.awaitFirstOrElse
import org.springframework.data.mongodb.gridfs.ReactiveGridFsOperations
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.util.*

@Service
class PhotoService(private val database: ReactiveGridFsOperations) : PhotoRepository {

    /**
     * @param environment the data wrapper QraphQL engine uses to keep request meta data.
     *
     * @return {@link Optional<UUID>} url id of a saved file
     */
    override suspend fun add(environment: DataFetchingEnvironment): Optional<UUID> = Flux.just(environment)
        .map {
            environment.getContext() as CustomGraphQLContext
        }.filter { it.fileParts.isNotEmpty() }
        .flatMap { Flux.fromIterable(it.fileParts) }
        .parallel()
        .flatMap { part ->
            val dataBuffer = FileType.image.factory().wrap(part.inputStream.readBytes())
            val url = UUID.randomUUID()
            val meta = BasicDBObject()
            meta["type"] = FileType.image.toString()
            meta["filename"] = part.submittedFileName
            meta["url"] = url
            database.store(Flux.just(dataBuffer), url.toString(), meta).map { url }
        }
        .runOn(Schedulers.parallel())
        .sequential()
        .collectList()
        .map { Optional.ofNullable(it.first()) }
        .awaitFirstOrElse { Optional.empty() }


    override suspend fun edit(environment: DataFetchingEnvironment): Optional<UUID> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(input: UUID, token: UUID): Boolean {
        TODO("Not yet implemented")
    }

}