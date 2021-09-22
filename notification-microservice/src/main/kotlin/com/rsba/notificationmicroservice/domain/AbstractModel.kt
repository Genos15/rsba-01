package com.rsba.notificationmicroservice.domain


interface AbstractModel {
    fun toJson(): String = try {
        ""
    } catch (e: Exception) {
        e.printStackTrace()
        "{}"
    }
}