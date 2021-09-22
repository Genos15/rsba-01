package com.rsba.file_microservice.service

import com.mongodb.BasicDBObject
import com.mongodb.DBObject
import com.rsba.file_microservice.context.CustomGraphQLContext
import com.rsba.file_microservice.domain.context.FileType
import com.rsba.file_microservice.repository.PhotoRepository
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.reactive.awaitFirstOrElse
import mu.KLogger
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.core.io.buffer.DefaultDataBuffer
import org.springframework.core.io.buffer.DefaultDataBufferFactory
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.data.mongodb.gridfs.GridFsCriteria.whereFilename
import org.springframework.data.mongodb.gridfs.ReactiveGridFsResource
import org.springframework.data.mongodb.gridfs.ReactiveGridFsTemplate
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.io.IOException
import java.util.*

@Service
class PhotoService(
    private val logger: KLogger,
    private val template: ReactiveGridFsTemplate,
) : PhotoRepository {

    val factory = DefaultDataBufferFactory()

    @Throws(IOException::class, RuntimeException::class)
    override suspend fun addPhoto(environment: DataFetchingEnvironment): UUID? = try {
        logger.warn { "+---- PhotoService -> addPhoto" }

        val context: CustomGraphQLContext = environment.getContext()

        if (context.fileParts.isEmpty() || context.fileParts.firstOrNull() == null) {
            throw RuntimeException("FILE NOT FOUND")
        }

        val part = context.fileParts.first()

        logger.warn { "uploading name = ${part.submittedFileName}, size = ${part.size}" }

        val dataBuffer: DefaultDataBuffer = factory.wrap(part.inputStream.readBytes())
        val body = Flux.just<DataBuffer>(dataBuffer)

        val myId = UUID.randomUUID();

        val meta: DBObject = BasicDBObject()
        meta.put("type", FileType.PHOTO)
        meta.put("filename", part.submittedFileName)
        meta.put("id", myId.toString())

        template.store(body, myId.toString(), meta)
            .map { myId }
            .awaitFirstOrElse { null }

    } catch (e: Exception) {
        logger.warn { "+---- PhotoService -> addPhoto -> error = ${e.message}" }
        null
    }

    @Throws(IOException::class, IllegalStateException::class)
    override suspend fun removePhoto(id: UUID): Boolean = try {
        logger.warn { "+---- PhotoService -> removePhoto" }
        template.delete(query(whereFilename().regex(id.toString())))
            .map { true }
            .onErrorResume {
                logger.warn { "+---- PhotoService -> removePhoto -> error = ${it.message}" }
                Mono.error { RuntimeException(it.message) }
            }
            .awaitFirstOrElse {
                logger.warn { "+---- PhotoService -> removePhoto -> Else" }
                false
            }
    } catch (e: Exception) {
        logger.warn { "+---- PhotoService -> removePhoto -> error = ${e.message}" }
        false
    }

    override fun retrievePhoto(id: UUID): Mono<ReactiveGridFsResource> = try {
        logger.warn { "+---- PhotoService -> retrievePhoto" }
        template.findOne(query(whereFilename().regex(id.toString())))
            .flatMap {
                template.getResource(it)
            }
    } catch (e: Exception) {
        logger.warn { "+---- PhotoService -> retrievePhoto -> error = ${e.message}" }
        Mono.error { RuntimeException(e.message) }
    }


}