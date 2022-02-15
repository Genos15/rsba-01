package com.rsba.tasks_microservice.resolver.query

import com.rsba.tasks_microservice.domain.model.*
import com.rsba.tasks_microservice.domain.repository.GanttRepository
import com.rsba.tasks_microservice.domain.security.TokenAnalyzer
import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import java.util.*

@Component
class GanttQueryResolver(val service: GanttRepository, private val deduct: TokenAnalyzer) :
    GraphQLQueryResolver, GenericRetrieveConnection {

    suspend fun retrieveGantt(id: UUID, environment: DataFetchingEnvironment): List<GanttData> =
        service.retrieve(id = id, token = deduct(environment = environment))

}