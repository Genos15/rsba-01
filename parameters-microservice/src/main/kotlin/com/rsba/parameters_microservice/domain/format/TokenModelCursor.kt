package com.rsba.parameters_microservice.domain.format

 import com.rsba.parameters_microservice.domain.model.TokenModel
 import com.rsba.parameters_microservice.domain.exception.CustomGraphQLError
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