package  com.rsba.usermicroservice.query.database

import com.rsba.usermicroservice.domain.input.LoginUserReturn
import com.rsba.usermicroservice.domain.model.User
import io.r2dbc.spi.Row
import io.r2dbc.spi.RowMetadata
import mu.KLogger
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Component
import java.util.*


@Component
class User2DataHandler(private val logger: KLogger) {

    fun all(row: Row?, meta: RowMetadata?): MutableList<User> = try {
        logger.warn { "+----- User2DataHandler -> all" }
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
        mutableListOf()
    }

    fun one(row: Row?, meta: RowMetadata?): Optional<User> = try {
        logger.warn { "+----- User2DataHandler -> one" }
        if (row != null) {
            val json = row.get(0, String::class.java)
            if (json != null) {
                Optional.ofNullable(Json{ ignoreUnknownKeys = true  }.decodeFromString<List<User>>("""$json""").firstOrNull())
            } else {
                Optional.empty()
            }
        } else {
            Optional.empty()
        }
    } catch (e: Exception) {
        Optional.empty()
    }

    fun oneToken(row: Row?, meta: RowMetadata?): Optional<LoginUserReturn> = try {
        logger.warn { "+----- User2DataHandler -> oneToken" }
        if (row != null) {
            val json = row.get(0, String::class.java)
            if (json != null) {
                Optional.ofNullable(Json{ ignoreUnknownKeys = true  }.decodeFromString<List<LoginUserReturn>>("""$json""").firstOrNull())
            } else {
                Optional.empty()
            }
        } else {
            Optional.empty()
        }
    } catch (e: Exception) {
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