package com.rsba.file_microservice.resolver.query

import com.rsba.file_microservice.aspect.AdminSecured
import com.rsba.file_microservice.configuration.request_helper.CursorUtil
import com.rsba.file_microservice.repository.PhotoRepository
import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import mu.KLogger
import java.util.*


@Component
class PhotoQueryResolver(
    val cursorUtil: CursorUtil,
    val service: PhotoRepository,
    val logger: KLogger,
) : GraphQLQueryResolver {

    @AdminSecured
    suspend fun retrieve(id: UUID, environment: DataFetchingEnvironment): Boolean {
        logger.warn { "+--- PhotoQueryResolver -> retrieve" }
        return false
    }

}