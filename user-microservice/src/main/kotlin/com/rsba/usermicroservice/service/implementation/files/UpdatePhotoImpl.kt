package com.rsba.usermicroservice.service.implementation.files

import com.mongodb.BasicDBObject
import com.rsba.usermicroservice.context.CustomGraphQLContext
import com.rsba.usermicroservice.domain.model.FileType
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.coroutines.reactor.mono
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.gridfs.GridFsCriteria.whereFilename
import org.springframework.data.mongodb.gridfs.ReactiveGridFsOperations
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.util.*
import javax.servlet.http.Part


interface UpdatePhotoImpl {

    fun fnAddPhoto(
        database: ReactiveGridFsOperations,
        environment: DataFetchingEnvironment
    ): Mono<Optional<UUID>> = Flux.just(environment)
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
            database.store(Flux.just(dataBuffer), url.toString(), meta)
                .map { url }
        }
        .runOn(Schedulers.parallel())
        .sequential()
        .collectList()
        .map { Optional.ofNullable(it.first()) }
        .onErrorResume {
            println("performAddPhoto->error = ${it.message}")
            throw it
        }


    fun fnEditPhoto(
        database: ReactiveGridFsOperations,
        part: Part,
        environment: DataFetchingEnvironment
    ): Mono<Optional<UUID>> = Mono.just(environment)
        .flatMap {
            val dataBuffer = FileType.image.factory().wrap(part.inputStream.readBytes())
            val url = UUID.randomUUID()
            val meta = BasicDBObject()
            meta["type"] = FileType.image.toString()
            meta["filename"] = part.submittedFileName
            meta["url"] = url

            database.store(Flux.just(dataBuffer), url.toString(), meta)
                .map { url }
        }
        .map { Optional.ofNullable(it) }
        .onErrorResume {
            println("editPhotoFn->error = ${it.message}")
            throw it
        }

    fun fnDeletePhoto(
        database: ReactiveGridFsOperations,
        filename: UUID,
    ): Mono<Boolean> = mono {
        database.find(Query.query(whereFilename().`is`(filename.toString())))
            .map {
                true
            }.awaitFirstOrElse { false }
    }

}