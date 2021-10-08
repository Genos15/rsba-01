package com.rsba.usermicroservice.configuration.scalar

import com.expediagroup.graphql.generator.hooks.SchemaGeneratorHooks
import graphql.kickstart.servlet.apollo.ApolloScalars
import graphql.language.StringValue
import graphql.scalars.ExtendedScalars
import graphql.schema.Coercing
import graphql.schema.GraphQLScalarType
import graphql.schema.GraphQLType
import org.springframework.context.annotation.Configuration
import java.time.OffsetDateTime
import java.util.*
import javax.servlet.http.Part
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

@Configuration
class UUIDScalarConfiguration : SchemaGeneratorHooks {
    override fun willGenerateGraphQLType(type: KType): GraphQLType? = when (type.classifier as? KClass<*>) {
        UUID::class -> graphqlUUIDType
        Any::class -> ExtendedScalars.Json
        Any::class -> ExtendedScalars.Object
        OffsetDateTime::class -> ExtendedScalars.DateTime
        Part::class -> ApolloScalars.Upload
        else -> null
    }
}

