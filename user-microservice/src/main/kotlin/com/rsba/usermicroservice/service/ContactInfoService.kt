package com.rsba.usermicroservice.service

import com.rsba.usermicroservice.domain.model.ContactInfo
import com.rsba.usermicroservice.query.database.ContactInfoDBHandler
import com.rsba.usermicroservice.query.database.ContactInfoDatabaseQueries
import com.rsba.usermicroservice.repository.ContactInfoRepository
import kotlinx.coroutines.reactive.awaitFirstOrElse
import mu.KLogger
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*
import java.util.stream.Collectors

@Service
class ContactInfoService(private val logger: KLogger, private val database: DatabaseClient) : ContactInfoRepository {
    override suspend fun onRetrieveByUserId(
        userIds: Set<UUID>,
        userId: UUID,
        page: Int,
        size: Int
    ): Map<UUID, List<ContactInfo>> =
        Flux.fromIterable(userIds)
            .flatMap { id ->
                return@flatMap database.sql(ContactInfoDatabaseQueries.onRetrieveByUserId(userId = id))
                    .map { row -> ContactInfoDBHandler.all(row = row) }
                    .first()
                    .map { AbstractMap.SimpleEntry(id, it.toList()) }
            }
            .collect(Collectors.toList())
            .map {
                val map = mutableMapOf<UUID, List<ContactInfo>>()
                it.forEach { element -> map[element.key] = element.value ?: emptyList() }
                return@map map.toMap()
            }
            .onErrorResume { Mono.empty() }
            .awaitFirstOrElse { emptyMap() }

}