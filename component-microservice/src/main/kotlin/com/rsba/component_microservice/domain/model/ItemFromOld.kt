package com.rsba.component_microservice.domain.model


import kotlinx.serialization.Serializable

@Serializable
data class ItemFromOld(val name: String, val description: String?)