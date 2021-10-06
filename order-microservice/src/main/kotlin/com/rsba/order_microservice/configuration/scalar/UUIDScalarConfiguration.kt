package com.rsba.order_microservice.configuration.scalar

import com.expediagroup.graphql.generator.hooks.SchemaGeneratorHooks
import graphql.scalars.ExtendedScalars
import graphql.schema.GraphQLType
import org.springframework.context.annotation.Configuration
import java.time.OffsetDateTime
import kotlin.reflect.KClass
import kotlin.reflect.KType

@Configuration
class UUIDScalarConfiguration : SchemaGeneratorHooks {
    override fun willGenerateGraphQLType(type: KType): GraphQLType? = when (type.classifier as? KClass<*>) {
        Any::class -> ExtendedScalars.Json
        Any::class -> ExtendedScalars.Object
        OffsetDateTime::class -> ExtendedScalars.DateTime
        else -> null
    }
}

