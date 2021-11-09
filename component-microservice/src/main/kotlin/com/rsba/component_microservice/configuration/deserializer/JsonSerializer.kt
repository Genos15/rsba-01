package com.rsba.component_microservice.configuration.deserializer

import graphql.language.ObjectValue
import graphql.language.StringValue
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json

class JsonSerializer : KSerializer<Any?> {

    private val serializer = String.serializer()

    override val descriptor = serializer.descriptor

    private val jsonHandler = Json {
        ignoreUnknownKeys = true
        isLenient = true
        prettyPrint = true
        encodeDefaults = true
        classDiscriminator = "#class"
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun deserialize(decoder: Decoder): Any? = try {
        jsonHandler.decodeFromString(DynamicLookupSerializer, decoder.toString())
    } catch (e: Exception) {
        e.printStackTrace()
        println("+JsonSerializer -> deserialize -> error = ${e.message}")
        null
    }

    override fun serialize(encoder: Encoder, value: Any?) = try {
        println("+JsonSerializer ->serialize->value= $value")
        val map = (value as? ObjectValue?)?.objectFields?.associate { it.name to (it.value as? StringValue)?.value }
            ?: mapOf()
//        val payload = JSONObject(map)
//        encoder.encodeString(value = payload.toString(2))
    } catch (e: Exception) {
        println("+JsonSerializer -> serialize-> error = ${e.message}")
    }

}