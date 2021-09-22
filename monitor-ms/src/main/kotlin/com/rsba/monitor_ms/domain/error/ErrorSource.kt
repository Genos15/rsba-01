package  com.rsba.monitor_ms.domain.error

import kotlinx.serialization.Serializable


@Serializable
data class ErrorSource(
    val code: String,
    val message: String
)