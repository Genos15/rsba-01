package com.rsba.component_microservice.domain.queries

import com.rsba.component_microservice.data.dao.AbstractModel
import com.rsba.component_microservice.domain.exception.CustomGraphQLError
import com.rsba.component_microservice.domain.format.JsonHandlerKotlin
import io.r2dbc.spi.Row
import kotlinx.serialization.json.*

object QueryCursor {

    fun all(row: Row): List<AbstractModel> = try {
        val payload = row.get(0, String::class.java)
        if (payload == null) {
            throw CustomGraphQLError(message = "Value is not nullable")
        } else {
            val payloadJson = payload.replace(" ", "").replace("[null]", "[]")
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

    fun one(row: Row): AbstractModel? {
        val payload = row.get(0, String::class.java) ?: throw CustomGraphQLError(message = "Value is not nullable")
        val payloadJson = payload.replace(" ", "").replace("[null]", "[]")
        var element = JsonHandlerKotlin.handler.parseToJsonElement(payloadJson)
        if (element is JsonArray && element.jsonArray.isNotEmpty()) {
            element = element.jsonArray[0].jsonObject
        }
        return JsonHandlerKotlin.handler.decodeFromJsonElement<AbstractModel?>(element)
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

    fun isTrue(row: Row): Boolean {
        return try {
            (meOrNull(row = row, index = 0) ?: 0) > 0
        } catch (e: Exception) {
            false
        }
    }
}