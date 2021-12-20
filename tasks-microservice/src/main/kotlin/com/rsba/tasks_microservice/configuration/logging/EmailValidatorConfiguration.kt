package com.rsba.parameters_microservice.configuration.logging

import org.apache.commons.validator.routines.EmailValidator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EmailValidatorConfiguration {

    @Bean
    fun myEmailValidator(): EmailValidator {
        return EmailValidator.getInstance()
    }
}