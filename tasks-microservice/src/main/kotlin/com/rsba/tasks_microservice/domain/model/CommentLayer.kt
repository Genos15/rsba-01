package com.rsba.tasks_microservice.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class CommentLayer {
    TASK,
    ORDER,
    REPORT,
}