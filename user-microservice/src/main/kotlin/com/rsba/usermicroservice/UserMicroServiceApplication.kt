package com.rsba.usermicroservice

//import graphql.Scalars
//import graphql.kickstart.servlet.apollo.ApolloScalars
//import graphql.schema.GraphQLObjectType
//import graphql.schema.GraphQLSchema
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cloud.openfeign.EnableFeignClients
//import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableConfigurationProperties
@EnableAspectJAutoProxy
@EnableScheduling
@EnableCaching
@EnableFeignClients
class UserMicroServiceApplication

fun main(args: Array<String>) {
    runApplication<UserMicroServiceApplication>(*args)
}

//@Bean
//fun schema(): GraphQLSchema {
//    return GraphQLSchema.newSchema()
//        .query(GraphQLObjectType.newObject()
//            .name("String")
//            .field { it.name("String").type(Scalars.GraphQLString) }
//            .build())
//        .query(GraphQLObjectType.newObject()
//            .name("Upload")
//            .field { it.name("Upload").type(ApolloScalars.Upload) }
//            .build())
//        .build()
//}
