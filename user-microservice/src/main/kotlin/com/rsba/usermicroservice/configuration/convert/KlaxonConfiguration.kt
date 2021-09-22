package com.rsba.usermicroservice.configuration.convert

import com.beust.klaxon.Klaxon
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class KlaxonConfiguration {

    @Bean
    fun defaultKlaxon(): Klaxon = Klaxon()
}