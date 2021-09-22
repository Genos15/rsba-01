package com.rsba.usermicroservice.query.database

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.rsba.usermicroservice.domain.model.Module
import io.r2dbc.spi.Row
import io.r2dbc.spi.RowMetadata
import mu.KotlinLogging
import java.util.*

object ModuleDatabaseHandler {

    private val logger = KotlinLogging.logger {}
    private val gson: Gson = GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss.S").create()
    private val mapUniversityType = object : TypeToken<List<Module>>() {}.type


    fun count(row: Row, meta: RowMetadata): Int {
        return try {
            meOrNull(row = row, index = 0) ?: 0
        } catch (e: Exception) {
            logger.warn { "≠------error = ${e.message}" }
            0
        }
    }


    fun readAll(row: Row, meta: RowMetadata): Map<UUID?, Module> {
        val result = mutableListOf<Module>()
        meta.columnNames.forEach {
            var json: String? = row.get(it, String::class.java)
            logger.warn { "module = $json" }
            json = json?.removePrefix("JsonByteArrayInput{")?.removeSuffix("}")
            try {
                result.addAll(gson.fromJson<List<Module>>(json, mapUniversityType))
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

}