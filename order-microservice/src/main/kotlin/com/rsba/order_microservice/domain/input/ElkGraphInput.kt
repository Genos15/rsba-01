package  com.rsba.order_microservice.domain.input

import kotlinx.serialization.Serializable

@Serializable
data class ElkGraphInput(val height: Int, val width: Int)