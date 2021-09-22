package com.rsba.file_microservice

import graphql.Scalars
import graphql.schema.GraphQLFieldDefinition
import graphql.schema.GraphQLObjectType
import graphql.schema.GraphQLSchema
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling


@SpringBootApplication(exclude = [MongoAutoConfiguration::class])
@EnableConfigurationProperties
@EnableScheduling
@EnableCaching
class FileMicroserviceApplication

fun main(args: Array<String>) {
    runApplication<FileMicroserviceApplication>(*args)
}

@Bean
fun schema(): GraphQLSchema {
    return GraphQLSchema.newSchema()
        .query(
            GraphQLObjectType.newObject()
                .name("user-query")
                .field { field: GraphQLFieldDefinition.Builder ->
                    field
                        .name("test")
                        .type(Scalars.GraphQLString)
                        .dataFetcher { "response" }
                }
                .build())
        .build()
}
