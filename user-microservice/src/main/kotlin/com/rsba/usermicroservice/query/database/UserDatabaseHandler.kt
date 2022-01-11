package com.rsba.usermicroservice.query.database

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.rsba.usermicroservice.domain.input.CreateAdminReturn
import com.rsba.usermicroservice.domain.input.LoginUserReturn
import com.rsba.usermicroservice.domain.input.SingleInviteUserReturn
import com.rsba.usermicroservice.domain.model.User
import io.r2dbc.spi.Row
import io.r2dbc.spi.RowMetadata
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import mu.KotlinLogging
import java.util.*

object UserDatabaseHandler {

    private val logger = KotlinLogging.logger {}
    private val gson: Gson = GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create()
//    private val GsonListUserMapperType = object : TypeToken<List<User>>() {}.type

    fun read(row: Row, meta: RowMetadata): Optional<User> {
        val result = mutableListOf<User>()

        meta.columnNames.forEach {
            var json: String? = row.get(it, String::class.java)
            logger.warn { json }
            json = json?.removePrefix("JsonByteArrayInput{")?.removeSuffix("}")
            val gsonListUserMapperType = object : TypeToken<List<User>>() {}.type
            try {
                result.addAll(gson.fromJson<List<User>>(json, gsonListUserMapperType))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return if (result.isNotEmpty()) {
            Optional.ofNullable(result.first())
        } else {
            Optional.empty()
        }
    }

    fun readCreateAdminReturn(row: Row, meta: RowMetadata): Optional<CreateAdminReturn> {
        val result = mutableListOf<CreateAdminReturn>()
        meta.columnNames.forEach {
            var json: String? = row.get(it, String::class.java)
            logger.warn { json }
            json = json?.removePrefix("JsonByteArrayInput{")?.removeSuffix("}")
            val gsonListUserMapperType = object : TypeToken<List<CreateAdminReturn>>() {}.type
            try {
                result.addAll(gson.fromJson<List<CreateAdminReturn>>(json, gsonListUserMapperType))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return if (result.isNotEmpty()) {
            Optional.ofNullable(result.first())
        } else {
            Optional.empty()
        }
    }

    fun readInvitationReturn(row: Row, meta: RowMetadata): Optional<SingleInviteUserReturn> {
        val result = mutableListOf<SingleInviteUserReturn>()
        meta.columnNames.forEach {
            var json: String? = row.get(it, String::class.java)

//            logger.warn { json }

            json = json?.removePrefix("JsonByteArrayInput{")?.removeSuffix("}")
            val gsonListUserMapperType = object : TypeToken<List<SingleInviteUserReturn>>() {}.type
            try {
                result.addAll(gson.fromJson<List<SingleInviteUserReturn>>(json, gsonListUserMapperType))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return if (result.isNotEmpty()) {
            Optional.ofNullable(result.first())
        } else {
            Optional.empty()
        }
    }

    fun readAsString(row: Row, meta: RowMetadata): Optional<String> {
        val result = mutableListOf<String?>()

        meta.columnNames.forEach {
            var json: String? = row.get(it, String::class.java)
            logger.warn { json }
            json = json?.removePrefix("JsonByteArrayInput{")?.removeSuffix("}")
            json = json?.replace("[", "")?.replace("]", "")
            result.add(json)
        }

        return if (result.isNotEmpty()) {
            logger.warn { "RETURNING " }
            logger.warn { result.first() }
            Optional.ofNullable(result.first())
        } else {
            Optional.empty()
        }
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


    fun readAll(row: Row, meta: RowMetadata): Map<UUID?, User> {
        val result = mutableListOf<User>()
        val mapType = object : TypeToken<List<User>>() {}.type

        meta.columnNames.forEach {
            var json: String? = row.get(it, String::class.java)
            logger.warn { "res = $json" }
            json = json?.removePrefix("JsonByteArrayInput{")?.removeSuffix("}")
            try {
                result.addAll(gson.fromJson<List<User>>(json, mapType))
            } catch (e: Exception) {
                logger.warn { "@------ $e" }
                logger.warn { "@------ ${e.message}" }
            }
        }

        return result.associateBy({ it.id }, { it })
    }

    fun all(row: Row?, meta: RowMetadata?): List<User> = try {
        logger.warn { "+----- UserDatabaseHandler -> all" }
        if (row != null) {
            val json = row.get(0, String::class.java)
            if (json != null) {
                Json { ignoreUnknownKeys = true }.decodeFromString("""$json""")
            } else {
                listOf()
            }
        } else {
            listOf()
        }
    } catch (e: Exception) {
        listOf()
    }


    fun one(row: Row?, meta: RowMetadata?): Optional<LoginUserReturn> = try {
        logger.warn { "+----- UserDatabaseHandler -> one" }

        if (row != null) {
            val json = row.get(0, String::class.java)
            logger.warn { json }
            if (json != null) {
                Optional.ofNullable(Json {
                    ignoreUnknownKeys = true
                }.decodeFromString<List<LoginUserReturn>>("""$json""").firstOrNull())
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


    fun oneInvitationReturn(row: Row?, meta: RowMetadata?): Optional<SingleInviteUserReturn> = try {
        logger.warn { "+----- UserDatabaseHandler -> oneInvitationReturn" }

        if (row != null) {
            val json = row.get(0, String::class.java)

            logger.warn { json }

            if (json != null) {
                Optional.ofNullable(Json {
                    ignoreUnknownKeys = true
                }.decodeFromString<List<SingleInviteUserReturn>>("""$json""").firstOrNull())
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

}