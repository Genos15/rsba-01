package com.rsba.order_microservice.data.context.dataloader

import com.rsba.order_microservice.domain.repository.ParameterRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import mu.KLogger
import org.dataloader.DataLoader
import org.springframework.stereotype.Service
import java.util.*

@Service
class ParameterDataLoaderImpl(private val logger: KLogger, private val service: ParameterRepository) {

    fun dataLoaderPotentialValuesOfParameter(userId: UUID): DataLoader<UUID, List<String>> {
        logger.warn { "+ParameterDataLoaderImpl->dataLoaderPotentialValuesOfParameter" }
        return DataLoader.newMappedDataLoader { ids, env ->
            logger.warn { env }
            GlobalScope.future {
                service.myPotentialValues(
                    ids = ids,
                    userId = userId
                )
            }
        }
    }

}