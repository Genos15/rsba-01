package com.rsba.getaway_ms

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class GetawayMsApplication

fun main(args: Array<String>) {
    runApplication<GetawayMsApplication>(*args)
}
