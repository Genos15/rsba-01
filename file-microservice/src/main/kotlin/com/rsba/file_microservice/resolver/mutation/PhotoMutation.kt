package com.rsba.file_microservice.resolver.mutation

import  com.rsba.file_microservice.aspect.AdminSecured
import com.rsba.file_microservice.repository.PhotoRepository
import graphql.kickstart.tools.GraphQLMutationResolver
import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import org.springframework.stereotype.Component
import java.util.*

@Component
class PhotoMutation(private val service: PhotoRepository, private val logger: KLogger) : GraphQLMutationResolver {

    @AdminSecured
    suspend fun addPhoto(environment: DataFetchingEnvironment): UUID? {
        logger.warn { "+-- PhotoMutation -> addPhoto" }
        return service.addPhoto(environment = environment)
    }

    @AdminSecured
    suspend fun removePhoto(input: UUID): Boolean {
        logger.warn { "+-- PhotoMutation -> removePhoto" }
        return service.removePhoto(id = input)
    }


}