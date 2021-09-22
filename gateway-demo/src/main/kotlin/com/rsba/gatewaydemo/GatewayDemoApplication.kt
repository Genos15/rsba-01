package com.rsba.gatewaydemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsConfigurationSource
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource


@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
@EnableCaching
class GatewayDemoApplication

fun main(args: Array<String>) {
    runApplication<GatewayDemoApplication>(*args)
}

@Bean
fun corsConfigurationSource(): CorsConfigurationSource {
    val configuration = CorsConfiguration()
    configuration.allowedOrigins = listOf("*")
    configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE")
//    configuration.allowCredentials = false
    configuration.allowedHeaders = listOf("*")
    configuration.exposedHeaders = listOf("x-auth-token", "xsrf-token")
    val source = UrlBasedCorsConfigurationSource()
    source.registerCorsConfiguration("*/**", configuration)
    return source
}