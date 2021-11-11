package com.rsba.component_microservice.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class MutationAction {
    ATTACH,
    DETACH
}