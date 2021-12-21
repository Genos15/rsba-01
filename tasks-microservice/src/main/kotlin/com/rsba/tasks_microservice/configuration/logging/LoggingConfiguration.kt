package com.rsba.tasks_microservice.configuration.logging

import mu.KLogger
import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class LoggingConfiguration {

    @Bean
    fun logger(): KLogger = KotlinLogging.logger {}
}