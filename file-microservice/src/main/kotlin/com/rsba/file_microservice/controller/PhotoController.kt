package com.rsba.file_microservice.controller

import com.rsba.file_microservice.domain.context.FileType
import com.rsba.file_microservice.repository.PhotoRepository
import mu.KLogger
import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.io.FileNotFoundException
import java.io.IOException
import java.util.*
import kotlin.jvm.Throws

@RestController
@RequestMapping("/photo")
class PhotoController(private val service: PhotoRepository, private val logger: KLogger) {

    @GetMapping("{id}")
    @Throws(IOException::class, FileNotFoundException::class, RuntimeException::class)
    fun get(@PathVariable id: UUID): Mono<ResponseEntity<InputStreamResource>> {
        logger.warn { "+---- PhotoController -> get" }
        return service.retrievePhoto(id = id)
            .flatMap {
                it.gridFSFile.map { res ->
                    val type: String? = res.metadata?.getString("type")
                    val filename: String? = res.metadata?.getString("filename")

                    logger.warn { res.metadata }

                    val isEqual =
                        type?.trim()
                            ?.equals(FileType.PHOTO.toString(), ignoreCase = true) ?: false

                    if (isEqual.not() || filename == null) {
                        throw  FileNotFoundException("FILE NOT FOUND")
                    }

                    filename
                }.flatMap { filename ->
                    it.inputStream.map { res -> InputStreamResource(res) }
                        .map { input ->
                            ResponseEntity.ok()
                                .header(
                                    HttpHeaders.CONTENT_DISPOSITION,
                                    "attachment; filename=\"$filename\""
                                )
                                .body(input)
                        }
                }
            }
    }

}