package com.rsba.usermicroservice.service.implementation

import com.mongodb.BasicDBObject
import com.rsba.usermicroservice.context.CustomGraphQLContext
import com.rsba.usermicroservice.domain.model.FileType
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.reactive.awaitFirstOrElse
import org.springframework.data.mongodb.gridfs.ReactiveGridFsOperations
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.util.*

interface AddPhotoImpl {

    fun performAddPhoto(
        database: ReactiveGridFsOperations,
        environment: DataFetchingEnvironment
    ): Mono<UUID> = Flux.just(environment)
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
        .map { it.first() }
        .onErrorResume {
            println("performAddPhoto->error = ${it.message}")
            throw it
        }

}