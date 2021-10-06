package com.rsba.usermicroservice.service.implementation.users

import com.rsba.usermicroservice.repository.PhotoRepository
import reactor.core.publisher.Mono
import java.io.InputStream
import java.util.*

interface RetrievePhotoImpl {

    fun retrievePhotoFn(input: UUID, fileManager: PhotoRepository): Mono<InputStream> =
        fileManager.retrievePhoto(id = input)
            .filter { it.exists() }
            .flatMap {
                it.inputStream
            }
            .onErrorResume {
                println("RetrievePhotoImpl->retrievePhotoImpl->error=${it.message}")
                throw it
            }
}