package com.rsba.order_microservice.exception

class CustomGraphQLError(message: String) : RuntimeException("[p:001]".plus(message))