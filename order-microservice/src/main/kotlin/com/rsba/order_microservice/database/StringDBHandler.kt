package  com.rsba.order_microservice.database

import com.rsba.order_microservice.configuration.json.JsonHandler
import com.rsba.order_microservice.domain.model.Technology
import io.r2dbc.spi.Row
import io.r2dbc.spi.RowMetadata
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import java.util.*

@OptIn(ExperimentalSerializationApi::class)
object StringDBHandler {
    internal inline fun <reified R : Any> String.convertToDataClass() =
        JsonHandler().decodeFromString<R>(this)


    fun all(row: Row?, meta: RowMetadata? = null): List<String> = try {
        if (row != null) {
            val json = row.get(0, String::class.java)
            if (json != null) {
                JsonHandler().decodeFromString("""$json""")
            } else {
                emptyList()
            }
        } else {
            emptyList()
        }
    } catch (e: Exception) {
        e.printStackTrace()
        emptyList()
    }

    fun one(row: Row?, meta: RowMetadata? = null): Optional<Technology> = try {
        if (row != null) {
            val json = row.get(0, String::class.java)
            if (json != null) {
                Optional.ofNullable(
                    JsonHandler().decodeFromString<List<Technology>>("""$json""").firstOrNull()
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