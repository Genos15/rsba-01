package com.rsba.order_microservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableConfigurationProperties
@EnableAspectJAutoProxy
@EnableScheduling
@EnableCaching
//@EnableDiscoveryClient
class OrderMicroserviceApplication

fun main(args: Array<String>) {
    runApplication<OrderMicroserviceApplication>(*args)
}

//@Bean
//fun schema(): GraphQLSchema {
//    return GraphQLSchema.newSchema()
//        .query(
//            GraphQLObjectType.newObject()
//                .name("user-query")
//                .field { field: GraphQLFieldDefinition.Builder ->
//                    field
//                        .name("test")
//                        .type(Scalars.GraphQLString)
//                        .dataFetcher { "response" }
//                }
//                .build())
//        .build()
//}