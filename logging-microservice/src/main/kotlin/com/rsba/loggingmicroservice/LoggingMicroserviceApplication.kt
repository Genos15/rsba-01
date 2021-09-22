package com.rsba.loggingmicroservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LoggingMicroserviceApplication

fun main(args: Array<String>) {
    runApplication<LoggingMicroserviceApplication>(*args)
}
