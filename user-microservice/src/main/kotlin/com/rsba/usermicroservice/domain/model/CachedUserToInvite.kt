package com.rsba.usermicroservice.domain.model

import com.rsba.usermicroservice.configuration.convert.KlaxonProps

class CachedUserToInvite(val company: String, val email: String) {
    fun toJson() = KlaxonProps.klaxon.toJsonString(this)
}