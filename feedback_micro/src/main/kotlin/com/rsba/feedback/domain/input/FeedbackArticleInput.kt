package com.rsba.feedback.domain.input

import com.rsba.feedback.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class FeedbackArticleInput(
    @Serializable(with = UUIDSerializer::class) val id: UUID? = null,
    val title: String? = null,
    val content: String? = null,
    val link: String? = null,
)
