package com.rsba.component_microservice.configuration.deserializer

import com.rsba.component_microservice.data.dao.*
import com.rsba.component_microservice.domain.format.ModelTypeCase
import com.rsba.component_microservice.domain.exception.CustomGraphQLError
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.*

object AbstractSerializer : JsonContentPolymorphicSerializer<AbstractModel>(AbstractModel::class) {

    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<out AbstractModel> {
        if (element is JsonArray) {
            return when (element.jsonArray[0].jsonObject["class"]?.jsonPrimitive?.content?.lowercase()) {
                ModelTypeCase.item_category.lowercase() -> ItemCategoryDao.serializer()
                ModelTypeCase.items.lowercase() -> ItemDao.serializer()
                ModelTypeCase.operations.lowercase() -> OperationDao.serializer()
                ModelTypeCase.departments.lowercase() -> DepartmentDao.serializer()
                ModelTypeCase.technologies.lowercase() -> TechnologyDao.serializer()
                ModelTypeCase.information_usage.lowercase() -> InformationUsageDao.serializer()
                else -> throw  CustomGraphQLError(message = "Unknown Module: key 'type' not found or does not matches any module type")
            }
        }

        return when (element.jsonObject["type"]?.jsonPrimitive?.content?.lowercase()) {
            ModelTypeCase.item_category.lowercase() -> ItemCategoryDao.serializer()
            ModelTypeCase.items.lowercase() -> ItemDao.serializer()
            ModelTypeCase.operations.lowercase() -> OperationDao.serializer()
            ModelTypeCase.departments.lowercase() -> DepartmentDao.serializer()
            ModelTypeCase.technologies.lowercase() -> TechnologyDao.serializer()
            ModelTypeCase.information_usage.lowercase() -> InformationUsageDao.serializer()
            else -> throw CustomGraphQLError(message = "Unknown Module: key 'type' not found or does not matches any module type")
        }
    }

}