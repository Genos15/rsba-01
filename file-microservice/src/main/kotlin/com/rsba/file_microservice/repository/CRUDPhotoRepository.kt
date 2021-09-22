package com.rsba.file_microservice.repository

import com.rsba.file_microservice.domain.model.Photo
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CRUDPhotoRepository : ReactiveCrudRepository<Photo, UUID>