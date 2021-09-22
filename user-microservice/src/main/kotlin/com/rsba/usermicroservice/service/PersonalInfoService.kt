package com.rsba.usermicroservice.service

import com.rsba.usermicroservice.domain.model.PersonalInfo
import com.rsba.usermicroservice.query.database.PersonalInfoDBHandler
import com.rsba.usermicroservice.query.database.PersonalInfoDatabaseQueries
import com.rsba.usermicroservice.repository.PersonalInfoRepository
import kotlinx.coroutines.reactive.awaitFirstOrElse
import mu.KLogger
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*
import java.util.stream.Collectors

@Service
class PersonalInfoService(private val logger: KLogger, private val database: DatabaseClient) : PersonalInfoRepository {
    override suspend fun onRetrieveByUserId(
        userIds: Set<UUID>,
        userId: UUID,
        page: Int,
        size: Int
    ): Map<UUID, PersonalInfo?> {
        logger.warn { "+PersonalInfoService -> onRetrieveByUserId" }
        return Flux.fromIterable(userIds)
            .flatMap { id ->
                return@flatMap database.sql(PersonalInfoDatabaseQueries.onRetrieveByUserId(userId = id))
                    .map { row -> PersonalInfoDBHandler.one(row = row) }
                    .first()
                    .map { AbstractMap.SimpleEntry(id, it) }
            }
            .collect(Collectors.toList())
            .map {
                val map = mutableMapOf<UUID, PersonalInfo?>()
                it.forEach { element -> map[element.key] = element.value.orElse(null) }
                return@map map.toMap()
            }
            .onErrorResume {
                logger.warn { "+PersonalInfoService -> onRetrieveByUserId -> error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { emptyMap() }
    }
}