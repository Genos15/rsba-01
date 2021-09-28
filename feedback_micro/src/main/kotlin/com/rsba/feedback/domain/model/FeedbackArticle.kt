package com.rsba.feedback.domain.model

import com.rsba.feedback.deserializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class FeedbackArticle(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val title: String,
    val content: String? = null,
    val link: String,
)