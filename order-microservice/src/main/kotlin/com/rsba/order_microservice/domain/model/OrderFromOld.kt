package com.rsba.order_microservice.domain.model


import kotlinx.serialization.Serializable

@Serializable
data class OrderFromOld(val name: String, val description: String?)