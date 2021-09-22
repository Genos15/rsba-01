package  com.rsba.order_microservice.database

import com.rsba.order_microservice.domain.model.DraggableWorkcenter
import io.r2dbc.spi.Row
import io.r2dbc.spi.RowMetadata
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.util.*


object WCAndTaskDBHandler {

    private val jsonHandler = Json {
        ignoreUnknownKeys = true
        isLenient = true
        prettyPrint = true
        encodeDefaults = true
        classDiscriminator = "#class"
    }

    fun all(row: Row?): List<DraggableWorkcenter> = try {
        if (row != null) {
            var json = row.get(0, String::class.java)
            json = json?.trim()?.replace("[null]", "[]")?.replace("null", "[]")
            if (json != null) {
                jsonHandler.decodeFromString("""$json""")
            } else {
                mutableListOf()
            }
        } else {
            mutableListOf()
        }
    } catch (e: Exception) {
        e.printStackTrace()
        listOf()
    }


    fun one(row: Row?): Optional<DraggableWorkcenter> = try {
        if (row != null) {
            var json = row.get(0, String::class.java)
            json = json?.trim()?.replace("[null]", "[]")?.replace("null", "[]")
            if (json != null) {
                Optional.ofNullable(
                    jsonHandler.decodeFromString<List<DraggableWorkcenter>>("""$json""").firstOrNull()
                )
            } else {
                Optional.empty()
            }
        } else {
            Optional.empty()
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Optional.empty()
    }


    private inline fun <reified N> meOrNull(row: Row, index: Int): N? = try {
        row.get(index, N::class.java)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }

    fun count(row: Row, meta: RowMetadata): Int {
        return try {
            meOrNull(row = row, index = 0) ?: 0
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }

}