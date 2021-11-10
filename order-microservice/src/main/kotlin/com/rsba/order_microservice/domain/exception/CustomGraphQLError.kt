package com.rsba.order_microservice.domain.exception

class CustomGraphQLError(message: String) : RuntimeException("[p:001]".plus(message))