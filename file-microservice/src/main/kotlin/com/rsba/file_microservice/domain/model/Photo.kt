package com.rsba.file_microservice.domain.model

import com.rsba.file_microservice.deserializer.StreamSerializer
import com.rsba.file_microservice.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.io.InputStream
import java.util.*

@Serializable
data class Photo(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    @Serializable(with = StreamSerializer::class) val stream: InputStream,
    val extension: String? = null,
    val compressed: Boolean? = false,
    val filename: String? = null
)

