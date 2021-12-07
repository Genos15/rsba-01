package com.rsba.component_microservice.data.dao

import com.example.ticketApp.deserializer.DateTimeSerializer
import com.rsba.component_microservice.configuration.deserializer.UUIDSerializer
import com.rsba.component_microservice.domain.format.ModelType
import com.rsba.component_microservice.domain.format.ModelTypeCase
import com.rsba.component_microservice.domain.model.ItemCategoryUsage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.util.*

@Serializable
@ModelType(_class = ModelTypeCase.item_category_usage)
data class ItemCategoryUsageDao(
    @Serializable(with = UUIDSerializer::class) override val id: UUID,
    val name: String,
    val description: String? = null,
    @Serializable(with = DateTimeSerializer::class) val from: OffsetDateTime? = null,
    @SerialName(value = "to") @Serializable(with = DateTimeSerializer::class) val _to: OffsetDateTime? = null,
    val usage: Int = 0,
) : AbstractModel() {

    val to: ItemCategoryUsage
        get() = ItemCategoryUsage(
            id = id,
            name = name,
            description = description,
            from = from,
            to = _to,
            usage = usage
        )
}
