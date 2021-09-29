package com.rsba.usermicroservice.service.implementation.files

import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.gridfs.GridFsCriteria
import org.springframework.data.mongodb.gridfs.ReactiveGridFsOperations
import org.springframework.data.mongodb.gridfs.ReactiveGridFsResource
import reactor.core.publisher.Mono
import java.util.*

interface RetrieveFileImpl {

    fun retrieveFileFn(
        id: UUID,
        database: ReactiveGridFsOperations,
    ): Mono<ReactiveGridFsResource> = database.findOne(Query.query(GridFsCriteria.whereFilename().regex(id.toString())))
        .flatMap { database.getResource(it) }

}