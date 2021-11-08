package com.rsba.component_microservice.domain.input

import com.rsba.component_microservice.configuration.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class ItemCategoryIndex(@Serializable(with = UUIDSerializer::class) val id: UUID, val index: Int)