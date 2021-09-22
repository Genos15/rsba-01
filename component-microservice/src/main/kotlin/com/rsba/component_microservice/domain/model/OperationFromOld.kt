package com.rsba.component_microservice.domain.model


import kotlinx.serialization.Serializable

@Serializable
data class OperationFromOld(val name: String, val description: String?, val time: Int? = 0)