package com.rsba.usermicroservice.deserializer

import com.google.gson.Gson
import com.rsba.usermicroservice.domain.model.PersonalInfo
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class PersonalInfoSerializer : KSerializer<PersonalInfo?> {

    private val serializer = String.serializer()

    override val descriptor = serializer.descriptor

    override fun deserialize(decoder: Decoder): PersonalInfo? = try {
        Gson().fromJson(serializer.deserialize(decoder), PersonalInfo::class.java)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }

    override fun serialize(encoder: Encoder, value: PersonalInfo?) = try {
        serializer.serialize(encoder, value.toString())
    } catch (e: Exception) {
        e.printStackTrace()
    }

}