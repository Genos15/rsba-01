package  com.rsba.order_microservice.deserializer

import com.rsba.order_microservice.domain.model.Task
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.serializer
import java.util.*

class MapUUIDTaskSerializer : KSerializer<Map<UUID, Task>> {



    private val uuidSerializer: KSerializer<UUID> = UUIDSerializer()
    private val taskSerializer: KSerializer<Task> = serializer()

    private val mapSerializer: KSerializer<Map<UUID, Task>> =  MapSerializer(uuidSerializer, taskSerializer)

    override val descriptor: SerialDescriptor = mapSerializer.descriptor

    override fun serialize(encoder: Encoder, value: Map<UUID, Task>) {
        mapSerializer.serialize(encoder, value.toMap())
    }

    override fun deserialize(decoder: Decoder): Map<UUID, Task> {


        return mapSerializer.deserialize(decoder)
    }
}