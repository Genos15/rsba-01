package  com.rsba.monitor_ms.database

import com.rsba.monitor_ms.domain.model.Agent
import io.r2dbc.spi.Row
import io.r2dbc.spi.RowMetadata
import mu.KLogger
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Component
import java.util.*


@Component
class AgentDataHandler(private val logger: KLogger) {

    fun all(row: Row?, meta: RowMetadata?): MutableList<Agent> = try {
        logger.warn { "+----- CategoryDataHandler -> all" }
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


    fun one(row: Row?, meta: RowMetadata?): Optional<Agent> = try {
        logger.warn { "+----- CategoryDataHandler -> oneItem" }
        if (row != null) {
            val json = row.get(0, String::class.java)
            logger.warn { json }
            if (json != null) {
                Optional.ofNullable(Json{ ignoreUnknownKeys = true  }.decodeFromString<List<Agent>>("""$json""").firstOrNull())
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