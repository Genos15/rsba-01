package com.rsba.component_microservice.database

import com.rsba.component_microservice.domain.model.Operation
import io.r2dbc.spi.Row
import io.r2dbc.spi.RowMetadata
import mu.KLogger
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Component
import java.util.*


@Component
class OperationDBHandler(private val logger: KLogger) {

    private val jsonHandler = Json { ignoreUnknownKeys = true }

    fun all(row: Row?, meta: RowMetadata?): MutableList<Operation> = try {
        logger.warn { "+----- OperationDBHandler -> allOperation" }
        if (row != null) {
            val json = row.get(0, String::class.java)
            if (json != null) {
                jsonHandler.decodeFromString("""$json""")
            } else {
                mutableListOf()
            }
        } else {
            mutableListOf()
        }
    } catch (e: Exception) {
        mutableListOf()
    }

    fun oneOperation(row: Row?, meta: RowMetadata?): Optional<Operation> = try {
        logger.warn { "+----- OperationDBHandler -> oneOperation" }
        if (row != null) {
            val json = row.get(0, String::class.java)
            if (json != null) {
                Optional.ofNullable(jsonHandler.decodeFromString<List<Operation>>("""$json""").firstOrNull())
            } else {
                Optional.empty()
            }
        } else {
            Optional.empty()
        }
    } catch (e: Exception) {
        Optional.empty()
    }


    private inline fun <reified N> meOrNull(row: Row, index: Int): N? = try {
        row.get(index, N::class.java)
    } catch (e: Exception) {
        logger.error { e.message }
        null
    }

    fun count(row: Row, meta: RowMetadata): Int {
        return try {
            meOrNull(row = row, index = 0) ?: 0
        } catch (e: Exception) {
            logger.warn { "≠------error = ${e.message}" }
            0
        }
    }

}