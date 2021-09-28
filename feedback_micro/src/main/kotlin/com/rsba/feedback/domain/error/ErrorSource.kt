package com.rsba.feedback.domain.error

import kotlinx.serialization.Serializable


@Serializable
data class ErrorSource(
    val code: String,
    val message: String
)