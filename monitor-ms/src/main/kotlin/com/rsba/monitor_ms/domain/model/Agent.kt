package  com.rsba.monitor_ms.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Agent(
    @SerialName("firstname") val firstName: String? = null,
    @SerialName("lastname") val lastName: String? = null,
    @SerialName("middlename") val middleName: String? = null,
    val phone: String? = null,
    val email: String? = null
)
