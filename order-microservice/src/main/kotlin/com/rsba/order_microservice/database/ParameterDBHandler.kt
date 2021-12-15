package  com.rsba.order_microservice.database

import com.rsba.order_microservice.configuration.json.JsonHandler
import io.r2dbc.spi.Row
import io.r2dbc.spi.RowMetadata
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import java.util.*
import com.rsba.order_microservice.domain.model.Parameter


@OptIn(ExperimentalSerializationApi::class)
object ParameterDBHandler {

    fun all(row: Row?, meta: RowMetadata? = null): List<Parameter> = try {
        if (row != null) {
            val json = row.get(0, String::class.java)
            if (json != null) {
                JsonHandler().decodeFromString("""$json""")
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

    fun one(row: Row?, meta: RowMetadata? = null): Optional<Parameter> = try {
        if (row != null) {
            val json = row.get(0, String::class.java)
            if (json != null) {
                Optional.ofNullable(
                    JsonHandler().decodeFromString<List<Parameter>>("""$json""").firstOrNull()
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

    fun count(row: Row, meta: RowMetadata? = null): Int {
        return try {
            meOrNull(row = row, index = 0) ?: 0
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }

}