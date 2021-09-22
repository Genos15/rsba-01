package com.rsba.discovery_ms

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

@SpringBootApplication
@EnableEurekaServer
class DiscoveryMsApplication

fun main(args: Array<String>) {
    runApplication<DiscoveryMsApplication>(*args)
}
