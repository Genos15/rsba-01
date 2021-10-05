package com.rsba.component_microservice.configuration.scalar

import com.expediagroup.graphql.generator.hooks.SchemaGeneratorHooks
import graphql.language.ObjectValue
import graphql.language.StringValue
import graphql.scalars.ExtendedScalars
import graphql.schema.Coercing
import graphql.schema.GraphQLScalarType
import graphql.schema.GraphQLType
import org.springframework.context.annotation.Configuration
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.KType

val graphqlUUIDType: GraphQLScalarType = GraphQLScalarType.newScalar()
    .name("UUID")
    .description("A type representing a formatted java.util.UUID")
    .coercing(UUIDCoercing)
    .build()

object UUIDCoercing : Coercing<UUID, String> {
    override fun parseValue(input: Any?): UUID = UUID.fromString(serialize(input))

    override fun parseLiteral(input: Any?): UUID? {
        val uuidString = (input as? StringValue)?.value
        return UUID.fromString(uuidString)
    }

    override fun serialize(dataFetcherResult: Any?): String = dataFetcherResult.toString()
}


object JSONCoercing : Coercing<ObjectValue?, Any> {

    override fun parseValue(input: Any?): ObjectValue? = try {
        ObjectValue.newObjectValue().additionalData("value", serialize(input)).build()
    } catch (e: Exception) {
        println { "+Json parseValue = ${e.message}" }
        null
    }

    override fun parseLiteral(input: Any?): ObjectValue? = try {
        val value = (input as? ObjectValue?)
        value
    } catch (e: Exception) {
        println { "+Json parseLiteral = ${e.message}" }
        null
    }

    override fun serialize(dataFetcherResult: Any?): String {
        println("+serialize->income = $dataFetcherResult")
        println { "+serialize->income = $dataFetcherResult" }
        println { "+serialize->income = ${dataFetcherResult.toString()}" }
        return dataFetcherResult.toString()
    }
}

@Configuration
class UUIDScalarConfiguration : SchemaGeneratorHooks {
    override fun willGenerateGraphQLType(type: KType): GraphQLType? = when (type.classifier as? KClass<*>) {
        UUID::class -> graphqlUUIDType
        Any::class -> ExtendedScalars.Json
        Map::class -> ExtendedScalars.Object
        OffsetDateTime::class -> ExtendedScalars.DateTime
        BigDecimal::class -> ExtendedScalars.GraphQLBigDecimal
        else -> null
    }
}

