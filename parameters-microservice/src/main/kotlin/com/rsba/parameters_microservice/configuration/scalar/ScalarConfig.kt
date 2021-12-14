package com.rsba.parameters_microservice.configuration.scalar

import graphql.scalars.ExtendedScalars
import graphql.schema.GraphQLScalarType
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ScalarConfig {

    @Bean
    fun jsonType(): GraphQLScalarType = ExtendedScalars.Json

    @Bean
    fun timestampType(): GraphQLScalarType = ExtendedScalars.DateTime

    @Bean
    fun objectType(): GraphQLScalarType = ExtendedScalars.Object

    @Bean
    fun bigDecimal(): GraphQLScalarType = ExtendedScalars.GraphQLBigDecimal
}