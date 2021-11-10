package com.rsba.order_microservice.resolver.suite

import com.rsba.order_microservice.data.context.dataloader.DataLoaderRegistryFactory
import com.rsba.order_microservice.domain.model.*
import graphql.kickstart.tools.GraphQLResolver
import graphql.schema.DataFetchingEnvironment
import mu.KLogger
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture

@Component
class DepartmentResolver(private val logger: KLogger) : GraphQLResolver<Department> {
    fun workingCenters(
        module: Department,
        env: DataFetchingEnvironment
    ): CompletableFuture<List<WorkingCenter>>? {
        logger.warn { "+DepartmentResolver -> workingCenters" }
        val dataLoader =
            env.getDataLoader<Department, List<WorkingCenter>>(DataLoaderRegistryFactory.WORKCENTER_OF_DEPARTMENT_IN_TASK_DATALOADER)
        return dataLoader?.load(module) ?: CompletableFuture.completedFuture(emptyList())
    }
}