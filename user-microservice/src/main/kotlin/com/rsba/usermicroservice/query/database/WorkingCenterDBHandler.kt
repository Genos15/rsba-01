package  com.rsba.usermicroservice.query.database

import com.rsba.usermicroservice.domain.model.WorkingCenter
import io.r2dbc.spi.Row
import io.r2dbc.spi.RowMetadata
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.util.*


object WorkingCenterDBHandler {

    private val jsonHandler = Json { ignoreUnknownKeys = true }

    fun all(row: Row?, meta: RowMetadata?): MutableList<WorkingCenter> = try {
        if (row != null) {
            val json = row.get(0, String::class.java)
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
        mutableListOf()
    }

    fun one(row: Row?, meta: RowMetadata?): Optional<WorkingCenter> = try {
        if (row != null) {
            val json = row.get(0, String::class.java)
            if (json != null) {
                Optional.ofNullable(jsonHandler.decodeFromString<List<WorkingCenter>>("""$json""").firstOrNull())
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