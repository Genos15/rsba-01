package  com.rsba.order_microservice.deserializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.*

object ListUUIDSerializer : KSerializer<List<UUID>> {

    private val serializer = ListSerializer(UUIDSerializer())

    override val descriptor = serializer.descriptor

    override fun deserialize(decoder: Decoder): List<UUID> =
        serializer.deserialize(decoder)

    override fun serialize(encoder: Encoder, value: List<UUID>) =
        serializer.serialize(encoder, value.toList())
}