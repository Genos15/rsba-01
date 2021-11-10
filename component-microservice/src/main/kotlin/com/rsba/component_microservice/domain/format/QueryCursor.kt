package com.rsba.component_microservice.domain.format

import com.rsba.component_microservice.data.dao.AbstractModel
import com.rsba.component_microservice.domain.exception.CustomGraphQLError
import io.r2dbc.spi.Row
import kotlinx.serialization.json.*

object QueryCursor {

    private val jsonHandler = Json {
        ignoreUnknownKeys = true
        isLenient = true
        prettyPrint = true
        encodeDefaults = true
        classDiscriminator = "#class"
    }

    fun all(row: Row): List<AbstractModel> = try {
        val payload = row.get(0, String::class.java)
        if (payload == null) {
            throw CustomGraphQLError(message = "Value is not nullable")
        } else {
            val payloadJson = payload.replace(" ", "").replace("[null]", "[]")
            val element = jsonHandler.parseToJsonElement(payloadJson)
            require(element is JsonArray)
            jsonHandler.decodeFromJsonElement(element)
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
        var element = jsonHandler.parseToJsonElement(payloadJson)
        if (element is JsonArray && element.jsonArray.isNotEmpty()) {
            element = element.jsonArray[0].jsonObject
        }
        return jsonHandler.decodeFromJsonElement<AbstractModel?>(element)
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