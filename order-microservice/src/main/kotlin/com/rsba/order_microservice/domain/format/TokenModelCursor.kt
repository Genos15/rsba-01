package com.rsba.order_microservice.domain.format

import com.rsba.order_microservice.domain.exception.CustomGraphQLError
import com.rsba.order_microservice.domain.model.TokenModel
import io.r2dbc.spi.Row
import io.r2dbc.spi.RowMetadata
import kotlinx.serialization.json.*

object TokenModelCursor {

    private val jsonHandler = Json {
        ignoreUnknownKeys = true
        isLenient = true
        prettyPrint = true
        encodeDefaults = true
        classDiscriminator = "#class"
    }

    fun all(row: Row): List<TokenModel> = try {
        val payload = row.get(0, String::class.java)
        if (payload == null) {
            throw CustomGraphQLError(message = "Value is not nullable")
        } else {
            val element = jsonHandler.parseToJsonElement(payload)
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

    fun one(row: Row): TokenModel {
        val payload = row.get(0, String::class.java) ?: throw CustomGraphQLError(message = "Value is not nullable")
        var element = jsonHandler.parseToJsonElement(payload)
        if (element is JsonArray && element.jsonArray.isNotEmpty()) {
            element = element.jsonArray[0].jsonObject
        }
        return jsonHandler.decodeFromJsonElement<TokenModel>(element)
    }

    private inline fun <reified N> meOrNull(row: Row, index: Int): N? = try {
        row.get(index, N::class.java)
    } catch (e: Exception) {
        null
    }

    fun count(row: Row, meta: RowMetadata? = null): Int {
        return try {
            meOrNull(row = row, index = 0) ?: 0
        } catch (e: Exception) {
            0
        }
    }

    fun isTrue(row: Row, meta: RowMetadata? = null): Boolean {
        return try {
            (meOrNull(row = row, index = 0) ?: 0) > 0
        } catch (e: Exception) {
            false
        }
    }
}