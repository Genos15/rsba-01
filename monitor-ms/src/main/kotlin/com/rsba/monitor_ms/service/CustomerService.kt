package  com.rsba.monitor_ms.service

import  com.rsba.monitor_ms.domain.input.*
import  com.rsba.monitor_ms.domain.model.Customer
import  com.rsba.monitor_ms.database.CustomerDataHandler
import  com.rsba.monitor_ms.database.CustomerDatabaseQuery
import  com.rsba.monitor_ms.repository.CustomerRepository
import kotlinx.coroutines.reactive.awaitFirstOrElse
import mu.KLogger
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.SynchronousSink
import java.util.*
import java.util.stream.Collectors

@Service
class CustomerService(
    private val logger: KLogger,
    private val database: DatabaseClient,
    private val queryHelper: CustomerDatabaseQuery,
    private val dataHandler: CustomerDataHandler
) : CustomerRepository {

    override suspend fun createOrEditCustomer(input: CreateOrEditCustomerInput, token: UUID): Optional<Customer> {
        logger.warn { "+------ CustomerService -> createOrEditCustomer" }
        return database.sql(queryHelper.onCreateOrEditCustomer(input = input, token = token))
            .map { row, meta -> dataHandler.one(row = row, meta = meta) }
            .first()
            .onErrorResume { Mono.empty() }
            .awaitFirstOrElse { Optional.empty() }
    }

    override suspend fun addEntityToCustomer(input: AddEntityToCustomerInput, token: UUID): Optional<Customer> {
        logger.warn { "+------ CustomerService -> addEntityToCustomer" }
        return database.sql(queryHelper.onAddEntityToCustomer(input = input, token = token))
            .map { row, meta -> dataHandler.one(row = row, meta = meta) }
            .first()
            .onErrorResume { Mono.empty() }
            .awaitFirstOrElse { Optional.empty() }
    }

    override suspend fun removeEntityOfCustomer(input: RemoveEntityOfCustomerInput, token: UUID): Optional<Customer> {
        logger.warn { "+------ CustomerService -> removeEntityOfCustomer" }
        return database.sql(queryHelper.onRemoveEntityOfCustomer(input = input, token = token))
            .map { row, meta -> dataHandler.one(row = row, meta = meta) }
            .first()
            .onErrorResume { Mono.empty() }
            .awaitFirstOrElse { Optional.empty() }
    }

    override suspend fun deleteCustomer(input: UUID, token: UUID): Int {
        logger.warn { "+------ CustomerService -> deleteCustomer" }

        return database.sql(queryHelper.onDeleteCustomer(input = input, token = token))
            .map { row, meta -> dataHandler.count(row = row, meta = meta) }
            .first()
            .onErrorResume { Mono.empty() }
            .awaitFirstOrElse { 0 }
    }

    override suspend fun retrieveAllCustomer(token: UUID): MutableList<Customer> {
        logger.warn { "+------ CustomerService -> retrieveAllCustomer" }
        return database.sql(queryHelper.onRetrieveAllCustomer(token = token))
            .map { row, meta -> dataHandler.all(row = row, meta = meta) }
            .first()
            .onErrorResume { Mono.error(RuntimeException("NO CUSTOMER FOUND")) }
            .awaitFirstOrElse { mutableListOf() }
    }

    override suspend fun retrieveCustomerOfOrder(
        ids: Set<UUID>,
        userId: UUID,
        page: Int,
        size: Int
    ): Map<UUID, Customer> {
        logger.warn { "+------ CustomerService -> retrieveCustomerOfOrder" }

        return Flux.fromIterable(ids)
            .flatMap { id ->
                return@flatMap database.sql(queryHelper.onRetrieveCustomerOfOrder(input = id))
                    .map { row, meta -> dataHandler.one(row = row, meta = meta) }
                    .first()
                    .handle { single: Optional<Customer>, sink: SynchronousSink<Customer> ->
                        if (single.isPresent) {
                            sink.next(single.get())
                        } else {
                            sink.error(RuntimeException("CUSTOMER NOT FOUND"))
                        }
                    }
                    .map { AbstractMap.SimpleEntry(id, it) }
                    .onErrorResume {
                        logger.warn { "error1 = ${it.message}" }
                        Mono.empty()
                    }
            }
            .collect(Collectors.toList())
            .map {
                val map = mutableMapOf<UUID, Customer>()
                it.forEach { element -> map[element.key] = element.value }
                return@map map.toMap()
            }
            .onErrorResume {
                logger.warn { "error2 = ${it.message}" }
                Mono.empty()
            }
            .awaitFirstOrElse { emptyMap() }

    }

    override suspend fun retrieveEntitiesOfCustomer(
        ids: Set<UUID>,
        userId: UUID,
        page: Int,
        size: Int
    ): Map<UUID, MutableList<Customer>> {
        logger.warn { "+------ CustomerService -> retrieveEntitiesOfCustomer" }
        return Flux.fromIterable(ids)
            .flatMap { id ->
                return@flatMap database.sql(queryHelper.onRetrieveEntitiesOfCustomer(input = id))
                    .map { row, meta -> dataHandler.all(row = row, meta = meta) }
                    .first()
                    .map { AbstractMap.SimpleEntry(id, it) }
            }
            .collect(Collectors.toList())
            .map { list ->
                val map = mutableMapOf<UUID, MutableList<Customer>>()
                list.forEach { element -> map[element.key] = element.value }
                return@map map.toMap()
            }
            .onErrorResume { Mono.empty() }
            .awaitFirstOrElse { emptyMap() }
    }

    override suspend fun retrieveOneCustomer(id: UUID, token: UUID): Optional<Customer> {
        logger.warn { "+---- CustomerService -> retrieveOneOrder" }
        return database.sql(queryHelper.onRetrieveOneCustomer(input = id, token = token))
            .map { row, meta -> dataHandler.one(row = row, meta = meta) }
            .first()
            .onErrorResume {
                logger.warn { "+---- CustomerService -> retrieveOneOrder -> error = ${it.message}" }
                Mono.error { RuntimeException(it.message) }
            }.awaitFirstOrElse { Optional.empty() }
    }
}
