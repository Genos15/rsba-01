package com.rsba.tasks_microservice.configuration.deserializer

import com.rsba.tasks_microservice.data.dao.*
import com.rsba.tasks_microservice.domain.format.ModelTypeCase
import com.rsba.tasks_microservice.domain.exception.CustomGraphQLError
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.*

object AbstractSerializer : JsonContentPolymorphicSerializer<AbstractModel>(AbstractModel::class) {

    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<out AbstractModel> {
        if (element is JsonArray) {
            return when (element.jsonArray[0].jsonObject["class"]?.jsonPrimitive?.content?.lowercase()) {
                ModelTypeCase.tasks.lowercase() -> TaskDao.serializer()
                ModelTypeCase.orders.lowercase() -> OrderDao.serializer()
                ModelTypeCase.operations.lowercase() -> OperationDao.serializer()
                ModelTypeCase.items.lowercase() -> ItemDao.serializer()
                ModelTypeCase.workcenters.lowercase() -> WorkcenterDao.serializer()
                ModelTypeCase.users.lowercase() -> UserDao.serializer()
                ModelTypeCase.tasks_set.lowercase() -> TaskSetDao.serializer()
                ModelTypeCase.comments.lowercase() -> CommentDao.serializer()
                ModelTypeCase.technologies.lowercase() -> TechnologyDao.serializer()
                ModelTypeCase.worklogs.lowercase() -> WorklogDao.serializer()
                ModelTypeCase.gantt_data.lowercase() -> GanttDataDao.serializer()
                else -> throw  CustomGraphQLError(message = "Unknown Module: key 'type' not found or does not matches any module type")
            }
        }

        return when (element.jsonObject["type"]?.jsonPrimitive?.content?.lowercase()) {
            ModelTypeCase.tasks.lowercase() -> TaskDao.serializer()
            ModelTypeCase.orders.lowercase() -> OrderDao.serializer()
            ModelTypeCase.operations.lowercase() -> OperationDao.serializer()
            ModelTypeCase.items.lowercase() -> ItemDao.serializer()
            ModelTypeCase.workcenters.lowercase() -> WorkcenterDao.serializer()
            ModelTypeCase.users.lowercase() -> UserDao.serializer()
            ModelTypeCase.tasks_set.lowercase() -> TaskSetDao.serializer()
            ModelTypeCase.comments.lowercase() -> CommentDao.serializer()
            ModelTypeCase.technologies.lowercase() -> TechnologyDao.serializer()
            ModelTypeCase.worklogs.lowercase() -> WorklogDao.serializer()
            ModelTypeCase.gantt_data.lowercase() -> GanttDataDao.serializer()
            else -> throw CustomGraphQLError(message = "Unknown Module: key 'type' not found or does not matches any module type")
        }
    }

}