package com.rsba.gatewaydemo.configuration.routes

import org.springframework.context.annotation.Configuration
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder

import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec
import org.springframework.cloud.gateway.route.builder.PredicateSpec
import org.springframework.context.annotation.Bean


@Configuration(proxyBeanMethods = false)
class RouteConfig {

    @Bean
    fun routes(builder: RouteLocatorBuilder): RouteLocator? {
        return builder.routes()
            .route { p: PredicateSpec ->
                p.path("/user-microservice/**")
                    .filters { f: GatewayFilterSpec -> f.removeRequestHeader("Cookie") }
                    .uri("lb://user-microservice")
            }
            .route { p: PredicateSpec ->
                p.path("/notification-microservice/**")
                    .filters { f: GatewayFilterSpec -> f.removeRequestHeader("Cookie") }
                    .uri("lb://notification-microservice")
            }
            .build()
    }
}