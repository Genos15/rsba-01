package com.rsba.usermicroservice.query.database

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.rsba.usermicroservice.domain.input.ModuleWithPermission
import com.rsba.usermicroservice.domain.input.PermissionOfModule
import com.rsba.usermicroservice.domain.model.Permission
import io.r2dbc.spi.Row
import io.r2dbc.spi.RowMetadata
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import mu.KotlinLogging
import java.util.*

object PermissionDatabaseHandler {

    private val logger = KotlinLogging.logger {}
    private val gson: Gson = GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss.S").create()
    private val mapType = object : TypeToken<List<Permission>>() {}.type


    fun count(row: Row, meta: RowMetadata): Int {
        return try {
            meOrNull(row = row, index = 0) ?: 0
        } catch (e: Exception) {
            logger.warn { "≠------error = ${e.message}" }
            0
        }
    }


    fun readAll(row: Row, meta: RowMetadata): Map<UUID?, Permission> {
        val result = mutableListOf<Permission>()
        meta.columnNames.forEach {
            var json: String? = row.get(it, String::class.java)
            logger.warn { "res = $json" }
            json = json?.removePrefix("JsonByteArrayInput{")?.removeSuffix("}")
            try {
                result.addAll(gson.fromJson<List<Permission>>(json, mapType))
            } catch (e: Exception) {
                logger.warn { "@------ $e" }
                logger.warn { "@------ ${e.message}" }
            }
        }

        return result.associateBy({ it.id }, { it })
    }

    private inline fun <reified N> meOrNull(row: Row, index: Int): N? = try {
        row.get(index, N::class.java)
    } catch (e: Exception) {
        logger.error { e.message }
        null
    }

    fun list(row: Row?, meta: RowMetadata?): MutableList<PermissionOfModule> = try {
        logger.warn { "+----- PermissionDatabaseHandler -> list" }
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