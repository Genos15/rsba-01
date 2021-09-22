package com.rsba.notificationmicroservice.configuration.logger

import mu.KLogger
import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class LoggingConfiguration {

    @Bean
    fun setLogger(): KLogger {
        return KotlinLogging.logger { }
    }

}