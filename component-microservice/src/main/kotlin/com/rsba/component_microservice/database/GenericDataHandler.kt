package com.rsba.component_microservice.database

import io.r2dbc.spi.Row
import io.r2dbc.spi.RowMetadata
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import mu.KLogger
import org.springframework.stereotype.Component
import java.util.*

@Component
class GenericDataHandler(private val logger: KLogger) {

    fun <T> one(row: Row?, meta: RowMetadata?): Optional<T> = try {
        logger.warn { "+----- GenericDataHandler -> one" }
        if (row != null) {
            val json = row.get(0, String::class.java)
            logger.warn { json }
            if (json != null) {
                Optional.ofNullable(Json{ ignoreUnknownKeys = true  }.decodeFromString<List<T>>("""$json""").firstOrNull())
            } else {
                Optional.empty()
            }
        } else {
            Optional.empty()
        }
    } catch (e: Exception) {
        logger.warn { "error = ${e.message}" }
        Optional.empty()
    }

    fun <T> list(row: Row?, meta: RowMetadata?): MutableList<T> = try {
        logger.warn { "+----- GenericDataHandler -> list" }
        if (row != null) {
            val json = row.get(0, String::class.java)
            logger.warn { json }
            if (json != null) {
                Json{ ignoreUnknownKeys = true  }.decodeFromString<MutableList<T>>("""$json""")
            } else {
                mutableListOf()
            }
        } else {
            mutableListOf()
        }
    } catch (e: Exception) {
        logger.warn { "error = ${e.message}" }
        mutableListOf()
    }
}