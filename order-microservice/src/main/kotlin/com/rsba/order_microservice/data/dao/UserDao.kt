package  com.rsba.order_microservice.data.dao

import com.rsba.order_microservice.configuration.deserializer.DateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.util.*
import com.rsba.order_microservice.configuration.deserializer.UUIDSerializer
import com.rsba.order_microservice.domain.format.ModelType
import com.rsba.order_microservice.domain.format.ModelTypeCase
import com.rsba.order_microservice.domain.model.User

@Serializable
@ModelType(_class = ModelTypeCase.users)
data class UserDao(
    @Serializable(with = UUIDSerializer::class) override val id: UUID,
    val login: String,
    @Serializable(with = DateTimeSerializer::class) val createdAt: OffsetDateTime,
    @Serializable(with = DateTimeSerializer::class) val editedAt: OffsetDateTime,
) : AbstractModel() {

    val to: User
        get() = User(
            id = id,
            createdAt = createdAt,
            editedAt = editedAt,
            name = login
        )
}

