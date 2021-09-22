package com.rsba.usermicroservice.domain.error

import com.rsba.usermicroservice.configuration.convert.KlaxonProps.klaxon

data class ErrorSource(
    val code: String,
    val message: String
) {
    fun toJson() = klaxon.toJsonString(this)

    companion object {
        fun fromJson(json: String) = klaxon.parse<ErrorSource>(json)
    }
}
