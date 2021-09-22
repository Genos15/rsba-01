package com.rsba.file_microservice.utils

import org.apache.commons.lang3.RandomStringUtils

object CodeUtils {
    fun forAccountConfirmation(length: Int): String = RandomStringUtils.randomAlphanumeric(length)
}