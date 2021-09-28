package com.rsba.feedback

import graphql.Scalars
import graphql.schema.GraphQLFieldDefinition
import graphql.schema.GraphQLObjectType
import graphql.schema.GraphQLSchema
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableConfigurationProperties
@EnableAspectJAutoProxy
@EnableScheduling
@EnableCaching
class FeedbackMicroApplication

fun main(args: Array<String>) {
    runApplication<FeedbackMicroApplication>(*args)
}

@Bean
fun schema(): GraphQLSchema {
    return GraphQLSchema.newSchema()
        .query(
            GraphQLObjectType.newObject()
                .name("feedback-article-query")
                .field { field: GraphQLFieldDefinition.Builder ->
                    field
                        .name("test")
                        .type(Scalars.GraphQLString)
                        .dataFetcher { "response" }
                }
                .build())
        .build()
}

