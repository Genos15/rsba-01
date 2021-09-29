package com.rsba.usermicroservice.restful

import com.rsba.usermicroservice.repository.UserRepository
import org.springframework.core.io.InputStreamResource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping("photo")
@CrossOrigin(origins = ["*"])
class PhotoController(private val service: UserRepository) {

    @GetMapping("/{id}")
    fun retrieve(@PathVariable id: UUID): Mono<ResponseEntity<InputStreamResource>> =
        service.retrievePhoto(input = id)
            .map {
                ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(InputStreamResource(it))
            }

}