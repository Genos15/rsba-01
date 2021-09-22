package  com.rsba.order_microservice.database

import  com.rsba.order_microservice.domain.model.Order
import io.r2dbc.spi.Row
import io.r2dbc.spi.RowMetadata
import mu.KLogger
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Component
import java.util.*


@Component
class OrderDataHandler(private val logger: KLogger) {

    fun all(row: Row?, meta: RowMetadata?): MutableList<Order> = try {
        logger.warn { "+----- ItemDataHandler -> allItem" }
        if (row != null) {
            val json = row.get(0, String::class.java)
            logger.warn { "ALL = $json" }
            if (json != null) {
                Json { isLenient = true }.decodeFromString("""$json""")
            } else {
                mutableListOf()
            }
        } else {
            mutableListOf()
        }
    } catch (e: Exception) {
        logger.warn { "≠---- OrderDataHandler -> all -> error = ${e.message}" }
        mutableListOf()
    }


    fun one(row: Row?, meta: RowMetadata?): Optional<Order> = try {
        logger.warn { "+----- ItemDataHandler -> oneItem" }
        if (row != null) {
            val json = row.get(0, String::class.java)
            logger.warn { "ONE = $json" }
            if (json != null) {
                Optional.ofNullable(
                    Json { ignoreUnknownKeys = true }.decodeFromString<List<Order>>("""$json""").firstOrNull()
                )
            } else {
                Optional.empty()
            }
        } else {
            Optional.empty()
        }
    } catch (e: Exception) {
        logger.warn { "≠---- OrderDataHandler -> one -> error = ${e.message}" }
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