package com.rsba.order_microservice.configuration.deserializer

import com.rsba.order_microservice.data.dao.*
import com.rsba.order_microservice.domain.exception.CustomGraphQLError
import com.rsba.order_microservice.domain.format.ModelTypeCase
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.*

object AbstractSerializer : JsonContentPolymorphicSerializer<AbstractModel>(AbstractModel::class) {

    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<out AbstractModel> {
        if (element is JsonArray) {
            return when (element.jsonArray[0].jsonObject["class"]?.jsonPrimitive?.content?.lowercase()) {
                ModelTypeCase.orders_completion_line.lowercase() -> OrderCompletionLineDao.serializer()
                ModelTypeCase.orders.lowercase() -> OrderDao.serializer()
                ModelTypeCase.customers.lowercase() -> CustomerDao.serializer()
                ModelTypeCase.order_types.lowercase() -> OrderTypeDao.serializer()
                ModelTypeCase.agents.lowercase() -> AgentDao.serializer()
                ModelTypeCase.items.lowercase() -> ItemDao.serializer()
                ModelTypeCase.tasks.lowercase() -> TaskDao.serializer()
                ModelTypeCase.technologies.lowercase() -> TechnologyDao.serializer()
                ModelTypeCase.departments.lowercase() -> GroupDao.serializer()
                ModelTypeCase.operations.lowercase() -> OperationDao.serializer()
                ModelTypeCase.items_statistics.lowercase() -> ItemStatisticsDao.serializer()
                ModelTypeCase.users.lowercase() -> UserDao.serializer()
                ModelTypeCase.departments_statistics.lowercase() -> DepartmentStatisticsDao.serializer()
                ModelTypeCase.item_category_statistics.lowercase() -> ItemCategoryStatisticsDao.serializer()
                ModelTypeCase.parameters.lowercase() -> ParameterDao.serializer()
                ModelTypeCase.worklogs.lowercase() -> WorklogDao.serializer()
                else -> throw  CustomGraphQLError(message = "Unknown Module: key 'type' not found or does not matches any module type")
            }
        }

        return when (element.jsonObject["type"]?.jsonPrimitive?.content?.lowercase()) {
            ModelTypeCase.orders_completion_line.lowercase() -> OrderCompletionLineDao.serializer()
            ModelTypeCase.orders.lowercase() -> OrderDao.serializer()
            ModelTypeCase.customers.lowercase() -> CustomerDao.serializer()
            ModelTypeCase.order_types.lowercase() -> OrderTypeDao.serializer()
            ModelTypeCase.agents.lowercase() -> AgentDao.serializer()
            ModelTypeCase.items.lowercase() -> ItemDao.serializer()
            ModelTypeCase.tasks.lowercase() -> TaskDao.serializer()
            ModelTypeCase.technologies.lowercase() -> TechnologyDao.serializer()
            ModelTypeCase.departments.lowercase() -> GroupDao.serializer()
            ModelTypeCase.operations.lowercase() -> OperationDao.serializer()
            ModelTypeCase.items_statistics.lowercase() -> ItemStatisticsDao.serializer()
            ModelTypeCase.users.lowercase() -> UserDao.serializer()
            ModelTypeCase.departments_statistics.lowercase() -> DepartmentStatisticsDao.serializer()
            ModelTypeCase.item_category_statistics.lowercase() -> ItemCategoryStatisticsDao.serializer()
            ModelTypeCase.parameters.lowercase() -> ParameterDao.serializer()
            ModelTypeCase.worklogs.lowercase() -> WorklogDao.serializer()
            else -> throw CustomGraphQLError(message = "Unknown Module: key 'type' not found or does not matches any module type")
        }
    }

}