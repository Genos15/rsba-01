package  com.rsba.monitor_ms.utils

import java.util.*

object UUIDUtils {
    fun uuidOrNull(content: String?): UUID? = try {
        UUID.fromString(content?.trim())
    } catch (e: Exception) {
        null
    }
}