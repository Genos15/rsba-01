package com.rsba.component_microservice.database

import com.rsba.component_microservice.domain.model.ItemCategory
import com.rsba.component_microservice.domain.model.Item
import io.r2dbc.spi.Row
import io.r2dbc.spi.RowMetadata
import mu.KLogger
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Component
import java.util.*


@Component
class ItemDataHandler(private val logger: KLogger) {

    fun allItem(row: Row?, meta: RowMetadata?): MutableList<Item> = try {
        logger.warn { "+----- ItemDataHandler -> allItem" }
        if (row != null) {
            val json = row.get(0, String::class.java)
            if (json != null) {
                Json{ ignoreUnknownKeys = true  }.decodeFromString("""$json""")
            } else {
                mutableListOf()
            }
        } else {
            mutableListOf()
        }
    } catch (e: Exception) {
        logger.warn { e.message }
        mutableListOf()
    }

    fun allCategory(row: Row?, meta: RowMetadata?): MutableList<ItemCategory> = try {
        logger.warn { "+----- ItemDataHandler -> allCategory" }
        if (row != null) {
            val json = row.get(0, String::class.java)
            if (json != null) {
                Json{ ignoreUnknownKeys = true  }.decodeFromString("""$json""")
            } else {
                mutableListOf()
            }
        } else {
            mutableListOf()
        }
    } catch (e: Exception) {
        logger.warn { e.message }
        mutableListOf()
    }

    fun oneItem(row: Row?, meta: RowMetadata?): Optional<Item> = try {
        logger.warn { "+----- ItemDataHandler -> oneItem" }
        if (row != null) {
            val json = row.get(0, String::class.java)
            logger.warn { json }
            if (json != null) {
                Optional.ofNullable(Json{ ignoreUnknownKeys = true  }.decodeFromString<List<Item>>("""$json""").firstOrNull())
            } else {
                Optional.empty()
            }
        } else {
            Optional.empty()
        }
    } catch (e: Exception) {
        logger.warn { e.message }
        Optional.empty()
    }

    fun oneCategory(row: Row?, meta: RowMetadata?): Optional<ItemCategory> = try {
        logger.warn { "+----- ItemDataHandler -> onCategory" }
        if (row != null) {
            val json = row.get(0, String::class.java)
            logger.warn { json }
            if (json != null) {
                Optional.ofNullable(Json{ ignoreUnknownKeys = true  }.decodeFromString<List<ItemCategory>>("""$json""").firstOrNull())
            } else {
                Optional.empty()
            }
        } else {
            Optional.empty()
        }
    } catch (e: Exception) {
        logger.warn { e.message }
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