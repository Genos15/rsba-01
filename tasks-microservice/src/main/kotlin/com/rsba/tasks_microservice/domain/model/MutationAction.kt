package com.rsba.tasks_microservice.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class MutationAction {
    ATTACH,
    DETACH
}