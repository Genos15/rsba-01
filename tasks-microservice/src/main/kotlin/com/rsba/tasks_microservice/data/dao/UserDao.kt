package com.rsba.tasks_microservice.data.dao

import com.rsba.tasks_microservice.configuration.deserializer.UUIDSerializer
import com.rsba.tasks_microservice.domain.format.ModelType
import com.rsba.tasks_microservice.domain.format.ModelTypeCase
import com.rsba.tasks_microservice.domain.model.User
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
@ModelType(_class = ModelTypeCase.users)
data class UserDao(
    @Serializable(with = UUIDSerializer::class) override val id: UUID,
    val firstname: String,
    val lastname: String,
    val middlename: String? = null,
    val workload: Float = 0f,
) : AbstractModel() {

    val to: User
        get() = User(
            id = id,
            firstname = firstname,
            lastname = lastname,
            middlename = middlename,
            workload = workload
        )
}