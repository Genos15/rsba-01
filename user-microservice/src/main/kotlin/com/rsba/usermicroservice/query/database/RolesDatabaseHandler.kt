package com.rsba.usermicroservice.query.database

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.rsba.usermicroservice.domain.input.ModuleWithPermission
import com.rsba.usermicroservice.domain.model.Role
import io.r2dbc.spi.Row
import io.r2dbc.spi.RowMetadata
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import mu.KotlinLogging
import java.util.*

object RolesDatabaseHandler {

    private val logger = KotlinLogging.logger {}

    private val gson: Gson = GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss.S").create()

    private val mapUniversityType = object : TypeToken<List<Role>>() {}.type

    fun readAll(row: Row, meta: RowMetadata): Map<UUID?, Role> {
        val result = mutableListOf<Role>()
        meta.columnNames.forEach {
            var json: String? = row.get(it, String::class.java)
            json = json?.removePrefix("JsonByteArrayInput{")?.removeSuffix("}")
            try {
                result.addAll(
                    gson.fromJson<List<Role>>(
                        json,
                        mapUniversityType
                    )
                )
            } catch (e: Exception) {
                logger.warn { "@------ $e" }
                logger.warn { "@------ ${e.message}" }
            }
        }

        return result.associateBy({ it.id }, { it })
    }

    fun count(row: Row, meta: RowMetadata): Int {
        return try {
            meOrNull(row = row, index = 0) ?: 0
        } catch (e: Exception) {
            logger.warn { "≠------error = ${e.message}" }
            0
        }
    }

    private inline fun <reified N> meOrNull(row: Row, index: Int): N? = try {
        row.get(index, N::class.java)
    } catch (e: Exception) {
        logger.error { e.message }
        null
    }

    fun one(row: Row?, meta: RowMetadata?): Optional<Role> = try {
        logger.warn { "+----- RolesDatabaseHandler -> read" }

        if (row != null) {
            val json = row.get(0, String::class.java)
            logger.warn { json }
            if (json != null) {
                Optional.ofNullable(Json{ ignoreUnknownKeys = true  }.decodeFromString<List<Role>>("""$json""").firstOrNull())
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

    fun list(row: Row?, meta: RowMetadata?): MutableList<ModuleWithPermission> = try {
        logger.warn { "+----- GenericDataHandler -> list" }
        if (row != null) {
            val json = row.get(0, String::class.java)
            logger.warn { json }
            if (json != null) {
                Json{ ignoreUnknownKeys = true  }.decodeFromString("""$json""")
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