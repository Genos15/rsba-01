package com.rsba.usermicroservice.configuration.test

import com.fasterxml.jackson.databind.ObjectMapper
import com.graphql.spring.boot.test.GraphQLTestSubscription
import com.graphql.spring.boot.test.GraphQLTestTemplate

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.core.io.ResourceLoader

//@Configuration
//@ConditionalOnWebApplication(type = Type.SERVLET)
//class GraphQLTestAutoConfiguration {
//    @Bean
//    @ConditionalOnMissingBean
//    fun graphQLTestUtils(
//        resourceLoader: ResourceLoader?,
//        @Autowired(required = false) restTemplate: TestRestTemplate,
//        @Value("\${graphql.servlet.mapping:/graphql}") graphqlMapping: String,
//        objectMapper: ObjectMapper
//    ): GraphQLTestTemplate{
//        return GraphQLTestTemplate(resourceLoader, restTemplate, graphqlMapping, objectMapper)
//    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    @ConditionalOnBean(ObjectMapper::class)
//    fun graphQLTestSubscription(
//        environment: Environment,
//        objectMapper: ObjectMapper,
//        @Value("\${graphql.servlet.subscriptions.websocket.path:subscriptions}") subscriptionPath: String
//    ): GraphQLTestSubscription {
//        return GraphQLTestSubscription(environment, objectMapper, subscriptionPath)
//    }
//}