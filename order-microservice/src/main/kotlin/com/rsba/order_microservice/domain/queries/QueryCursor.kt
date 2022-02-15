package com.rsba.order_microservice.domain.queries

import com.rsba.order_microservice.data.dao.AbstractModel
import com.rsba.order_microservice.domain.exception.CustomGraphQLError
import com.rsba.order_microservice.domain.format.JsonHandlerKotlin
import io.r2dbc.spi.Row
import kotlinx.serialization.json.*
import java.util.*

object QueryCursor {

    fun all(row: Row): List<AbstractModel> = try {
        val payload = row.get(0, String::class.java)
        if (payload == null) {
            throw CustomGraphQLError(message = "Value is not nullable")
        } else {
            val payloadJson = if (payload.replace(" ", "").contains("[null]", ignoreCase = true)) {
                payload.replace(" ", "").replace("[null]", "[]")
            } else {
                payload.replace("[null]", "[]")
            }

            val element = JsonHandlerKotlin.handler.parseToJsonElement(payloadJson)
            require(element is JsonArray)
            JsonHandlerKotlin.handler.decodeFromJsonElement(element)
        }
    } catch (e: Exception) {
        if (e is CustomGraphQLError) {
            emptyList()
        } else {
            throw e
        }
    }

    fun one(row: Row): Optional<AbstractModel> {
        val payload = row.get(0, String::class.java)
            ?: return Optional.empty()/* ?: throw CustomGraphQLError(message = "Value is not nullable")*/
        val payloadJson = if (payload.replace(" ", "").contains("[null]", ignoreCase = true)) {
            payload.replace(" ", "").replace("[null]", "[]")
        } else {
            payload.replace("[null]", "[]")
        }
        var element = JsonHandlerKotlin.handler.parseToJsonElement(payloadJson)
        if (element is JsonArray && element.jsonArray.isNotEmpty()) {
            element = element.jsonArray[0].jsonObject
        }
        return Optional.ofNullable(JsonHandlerKotlin.handler.decodeFromJsonElement<AbstractModel?>(element))
    }

    private inline fun <reified N> meOrNull(row: Row, index: Int): N? = try {
        row.get(index, N::class.java)
    } catch (e: Exception) {
        null
    }

    fun count(row: Row): Int {
        return try {
            meOrNull(row = row, index = 0) ?: 0
        } catch (e: Exception) {
            0
        }
    }

    fun countAsString(row: Row): String? {
        return try {
            meOrNull(row = row, index = 0)
        } catch (e: Exception) {
            null
        }
    }

    fun isTrue(row: Row): Boolean {
        return try {
            (meOrNull(row = row, index = 0) ?: 0) > 0
        } catch (e: Exception) {
            false
        }
    }
}