package com.rsba.usermicroservice.domain.model

import com.rsba.usermicroservice.configuration.convert.KlaxonProps

class CachedUserContact(val login: String, val email: String) {
    fun toJson() = KlaxonProps.klaxon.toJsonString(this)
}