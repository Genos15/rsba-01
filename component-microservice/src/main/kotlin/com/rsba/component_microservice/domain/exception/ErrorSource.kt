package com.rsba.component_microservice.domain.exception

import kotlinx.serialization.Serializable


@Serializable
data class ErrorSource(
    val code: String,
    val message: String
)