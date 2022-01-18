package com.rsba.tasks_microservice.data.dao

import com.rsba.tasks_microservice.configuration.deserializer.DateTimeSerializer
import com.rsba.tasks_microservice.configuration.deserializer.UUIDSerializer
import com.rsba.tasks_microservice.domain.format.ModelType
import com.rsba.tasks_microservice.domain.format.ModelTypeCase
import com.rsba.tasks_microservice.domain.model.Comment
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.util.*

@Serializable
@ModelType(_class = ModelTypeCase.comments)
data class CommentDao(
    @Serializable(with = UUIDSerializer::class) override val id: UUID,
    val comment: String,
    @Serializable(with = DateTimeSerializer::class) val createdAt: OffsetDateTime,
    @Serializable(with = DateTimeSerializer::class) val editedAt: OffsetDateTime,
) : AbstractModel() {

    val to: Comment
        get() = Comment(
            id = id,
            comment = comment,
            createdAt = createdAt,
            editedAt = editedAt
        )
}