package com.rsba.order_microservice.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class ParameterType {
    ITEM,
    TASK
}