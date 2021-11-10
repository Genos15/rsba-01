package com.rsba.order_microservice.domain.format

import com.rsba.order_microservice.data.dao.AbstractModel
import com.rsba.order_microservice.domain.exception.CustomGraphQLError
import io.r2dbc.spi.Row
import kotlinx.serialization.json.*
import mu.KLogger
import java.util.*

abstract class IQueryCursorImpl(private val logger: KLogger) {

    fun deserializeList(row: Row): List<AbstractModel> = try {
        val payload = row.get(0, String::class.java)
        if (payload == null) {
            throw CustomGraphQLError(message = "Something went wrong while retrieve the list of elements")
        } else {
            val element = JsonHandlerKotlin.handler.parseToJsonElement(payload)
            require(element is JsonArray)
            JsonHandlerKotlin.handler.decodeFromJsonElement(element)
        }
    } catch (e: Exception) {
        if (e is CustomGraphQLError) {
            throw e
        } else {
            logger.warn { "deserializeList=${e.message}" }
            emptyList()
        }
    }

    fun deserialize(row: Row): Optional<AbstractModel> = try {
        val payload = row.get(0, String::class.java)
        if (payload == null) {
            throw CustomGraphQLError(message = "Something went wrong while retrieve the element")
        } else {
            var element = JsonHandlerKotlin.handler.parseToJsonElement(payload)
            if (element is JsonArray && element.jsonArray.isNotEmpty()) {
                element = element.jsonArray[0].jsonObject
            }
            val outcome = JsonHandlerKotlin.handler.decodeFromJsonElement<AbstractModel>(element)
            Optional.ofNullable(outcome)
        }
    } catch (e: Exception) {
        if (e is CustomGraphQLError) {
            throw e
        } else {
            logger.warn { "deserialize=${e.message}" }
            Optional.empty()
        }
    }

}