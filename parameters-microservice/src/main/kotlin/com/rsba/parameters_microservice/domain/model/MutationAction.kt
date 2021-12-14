package com.rsba.parameters_microservice.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class MutationAction {
    ATTACH,
    DETACH
}