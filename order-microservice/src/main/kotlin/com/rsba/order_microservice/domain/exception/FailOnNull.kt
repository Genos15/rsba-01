package com.rsba.order_microservice.domain.exception

fun failOnNull(message: String = "Value should not be null"): Nothing =
    throw AssertionError(message)


