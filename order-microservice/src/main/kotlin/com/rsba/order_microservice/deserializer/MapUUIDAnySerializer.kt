package  com.rsba.order_microservice.deserializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.serializer
import java.util.*

class MapUUIDAnySerializer : KSerializer<Map<UUID, Any>> {

    private val uuidSerializer: KSerializer<UUID> = serializer()
    private val anySerializer: KSerializer<Any> = serializer()

    private val mapSerializer = MapSerializer(uuidSerializer, anySerializer)

    override val descriptor: SerialDescriptor = mapSerializer.descriptor

    override fun serialize(encoder: Encoder, value: Map<UUID, Any>) {
        mapSerializer.serialize(encoder, value.toMap())
    }

    override fun deserialize(decoder: Decoder): Map<UUID, Any> {
        return mapSerializer.deserialize(decoder)
    }
}