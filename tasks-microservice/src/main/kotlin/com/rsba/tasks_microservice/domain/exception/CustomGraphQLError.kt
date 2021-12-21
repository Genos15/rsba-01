package com.rsba.tasks_microservice.domain.exception

class CustomGraphQLError(message: String) : RuntimeException("[p:001]".plus(message))