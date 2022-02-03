package com.rsba.tasks_microservice.data.dao

import com.rsba.tasks_microservice.configuration.deserializer.DateTimeSerializer
import com.rsba.tasks_microservice.configuration.deserializer.UUIDSerializer
import com.rsba.tasks_microservice.domain.format.ModelType
import com.rsba.tasks_microservice.domain.format.ModelTypeCase
import com.rsba.tasks_microservice.domain.model.Worklog
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.util.*

@Serializable
@ModelType(_class = ModelTypeCase.worklogs)
data class WorklogDao(
    @Serializable(with = UUIDSerializer::class) override val id: UUID,
    val title: String? = null,
    val contentTitle: String? = null,
    val content: String? = null,
    val details: String? = null,
    @Serializable(with = DateTimeSerializer::class) val createdAt: OffsetDateTime,
) : AbstractModel() {

    val to: Worklog
        get() = Worklog(
            id = id,
            title = title,
            contentTitle = contentTitle,
            content = content,
            details = details,
            createdAt = createdAt,
        )
}
