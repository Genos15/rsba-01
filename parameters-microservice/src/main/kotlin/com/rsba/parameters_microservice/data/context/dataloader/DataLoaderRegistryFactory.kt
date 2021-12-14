package com.rsba.parameters_microservice.data.context.dataloader

import org.dataloader.*
import org.springframework.stereotype.Component
import java.util.*

@Component
class DataLoaderRegistryFactory(private val parameter: ParameterDataLoaderImpl) {

    companion object {
//        const val LOADER_FACTORY_VALUES_OF_PARAMETER = "VALUES_OF_PARAMETER"
    }

    fun create(instanceId: UUID): DataLoaderRegistry {
        val registry = DataLoaderRegistry()
//        registry.register(LOADER_FACTORY_VALUES_OF_PARAMETER, parameter.dataLoaderValues(userId = instanceId))
        return registry
    }

}

