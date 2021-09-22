package com.rsba.usermicroservice.domain.model

import com.google.gson.Gson
import com.rsba.usermicroservice.configuration.convert.KlaxonProps

interface AbstractModel {
    fun toJson(): String = try {
        Gson().toJson(this)
    } catch (e: Exception) {
        e.printStackTrace()
        "{}"
    }

    companion object {
        fun fromJson(json: String) = KlaxonProps.klaxon.parse<User>(json)
    }
}