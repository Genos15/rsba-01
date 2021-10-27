package com.rsba.order_microservice.configuration.json

import kotlinx.serialization.json.Json

object JsonHandler {
    
    operator fun invoke() = Json {
        ignoreUnknownKeys = true
        isLenient = true
        prettyPrint = true
        encodeDefaults = true
        classDiscriminator = "#class"
    }
}