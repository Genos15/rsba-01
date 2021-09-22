package  com.rsba.order_microservice.database

import com.rsba.order_microservice.domain.model.OrderFromOld
import io.r2dbc.spi.Row
import io.r2dbc.spi.RowMetadata
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.util.*


object ExOrderDBHandler {

    private val jsonHandler = Json {
        ignoreUnknownKeys = true
        isLenient = true
        prettyPrint = true
        encodeDefaults = true
        classDiscriminator = "#class"
    }

    fun all(income: String): List<OrderFromOld> = try {
        var toDo = income
        if (income.startsWith("[[")) {
            toDo = toDo.removePrefix("[")
            toDo = toDo.removeSuffix("]")
        }
        jsonHandler.decodeFromString(toDo)
    } catch (e: Exception) {
        e.printStackTrace()
        listOf()
    }

    fun one(row: Row?, meta: RowMetadata?): Optional<OrderFromOld> = try {
        if (row != null) {
            val json = row.get(0, String::class.java)
            if (json != null) {
                Optional.ofNullable(
                    jsonHandler.decodeFromString<List<OrderFromOld>>("""$json""").firstOrNull()
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