package com.rsba.feedback.context.dataloader

import org.dataloader.*
import org.springframework.stereotype.Component
import java.util.*

@Component
class DataLoaderRegistryFactory {

    fun create(instanceId: UUID): DataLoaderRegistry {
//        val registry = DataLoaderRegistry()
//        return registry
        return DataLoaderRegistry()
    }

}

