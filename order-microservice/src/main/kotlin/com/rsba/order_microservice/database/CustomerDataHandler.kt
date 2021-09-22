package  com.rsba.order_microservice.database

import  com.rsba.order_microservice.domain.model.Customer
import io.r2dbc.spi.Row
import io.r2dbc.spi.RowMetadata
import mu.KLogger
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Component
import java.util.*


@Component
class CustomerDataHandler(private val logger: KLogger) {

    fun all(row: Row?, meta: RowMetadata?): MutableList<Customer> = try {
        logger.warn { "+----- CustomerDataHandler -> all" }
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
        logger.warn { "≠---- CustomerDataHandler -> all -> error = ${e.message}" }
        mutableListOf()
    }

    fun one(row: Row?, meta: RowMetadata?): Optional<Customer> = try {
        logger.warn { "+----- CustomerDataHandler -> one" }
        if (row != null) {
            val json = row.get(0, String::class.java)
            if (json != null) {
                Optional.ofNullable(Json{ ignoreUnknownKeys = true  }.decodeFromString<List<Customer>>("""$json""").firstOrNull())
            } else {
                Optional.empty()
            }
        } else {
            Optional.empty()
        }
    } catch (e: Exception) {
        logger.warn { "≠---- CustomerDataHandler -> one -> error = ${e.message}" }
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