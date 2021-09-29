package com.rsba.usermicroservice.exception

class CustomGraphQLError(message: String) : RuntimeException("[p:001]".plus(message))