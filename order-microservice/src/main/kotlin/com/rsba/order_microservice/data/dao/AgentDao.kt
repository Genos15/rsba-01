package  com.rsba.order_microservice.data.dao

import kotlinx.serialization.Serializable
import java.util.*
import com.rsba.order_microservice.configuration.deserializer.UUIDSerializer
import com.rsba.order_microservice.domain.format.ModelType
import com.rsba.order_microservice.domain.format.ModelTypeCase
import com.rsba.order_microservice.domain.model.Agent

@Serializable

@ModelType(_class = ModelTypeCase.agents)
data class AgentDao(
    @Serializable(with = UUIDSerializer::class) override val id: UUID,
    val firstName: String? = null,
    val lastName: String? = null,
    val middleName: String? = null,
    val phone: String? = null,
    val email: String? = null
) : AbstractModel() {

    val to: Agent
        get() = Agent(
            firstName = firstName,
            lastName = lastName,
            middleName = middleName,
            phone = phone,
            email = email
        )
}
