package com.rsba.parameters_microservice.configuration.scalar

import com.expediagroup.graphql.generator.hooks.SchemaGeneratorHooks
import graphql.scalars.ExtendedScalars
import graphql.schema.GraphQLType
import org.springframework.context.annotation.Configuration
import java.math.BigDecimal
import java.time.OffsetDateTime
import kotlin.reflect.KClass
import kotlin.reflect.KType

@Configuration
class ScalarConfiguration : SchemaGeneratorHooks {
    override fun willGenerateGraphQLType(type: KType): GraphQLType? = when (type.classifier as? KClass<*>) {
        Any::class -> ExtendedScalars.Json
        Map::class -> ExtendedScalars.Object
        OffsetDateTime::class -> ExtendedScalars.DateTime
        BigDecimal::class -> ExtendedScalars.GraphQLBigDecimal
        else -> null
    }
}

