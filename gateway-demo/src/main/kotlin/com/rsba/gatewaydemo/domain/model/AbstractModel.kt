package com.rsba.gatewaydemo.domain.model

import com.google.gson.Gson


interface AbstractModel {
    companion object {
        private val gson = Gson()
        fun <T : AbstractModel> fromJson(json: String, _class: Class<T>): T = gson.fromJson(json, _class) as T
    }
}