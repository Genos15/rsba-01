package com.rsba.parameters_microservice.domain.format

import kotlinx.serialization.json.*

object JsonHandlerKotlin {
    val handler = Json {
        ignoreUnknownKeys = true
        isLenient = true
        prettyPrint = true
        encodeDefaults = true
        classDiscriminator = "#class"
    }
}