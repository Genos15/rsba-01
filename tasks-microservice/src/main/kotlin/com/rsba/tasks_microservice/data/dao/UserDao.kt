package com.rsba.tasks_microservice.data.dao

import com.rsba.tasks_microservice.configuration.deserializer.DateTimeSerializer
import com.rsba.tasks_microservice.configuration.deserializer.UUIDSerializer
import com.rsba.tasks_microservice.domain.format.ModelType
import com.rsba.tasks_microservice.domain.format.ModelTypeCase
import com.rsba.tasks_microservice.domain.model.User
import com.rsba.tasks_microservice.domain.model.UserActivity
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.util.*

@Serializable
@ModelType(_class = ModelTypeCase.users)
data class UserDao(
    @Serializable(with = UUIDSerializer::class) override val id: UUID,
    val firstname: String,
    val lastname: String,
    val middlename: String? = null,
    val workload: Float = 0f,
    @Serializable(with = DateTimeSerializer::class) val estimatedStartDate: OffsetDateTime? = null,
    val activity: List<UserActivity>? = emptyList()
) : AbstractModel() {

    val to: User
        get() = User(
            id = id,
            firstname = firstname,
            lastname = lastname,
            middlename = middlename,
            workload = workload,
            estimatedStartDate = estimatedStartDate,
            activity = activity
        )
}