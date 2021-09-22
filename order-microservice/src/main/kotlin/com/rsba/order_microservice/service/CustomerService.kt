package  com.rsba.order_microservice.service

import  com.rsba.order_microservice.domain.input.*
import  com.rsba.order_microservice.domain.model.Customer
import  com.rsba.order_microservice.database.CustomerDataHandler
import  com.rsba.order_microservice.database.CustomerDatabaseQuery
import  com.rsba.order_microservice.repository.CustomerRepository
import kotlinx.coroutines.reactive.awaitFirstOrElse
import mu.KLogger
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.util.*
import java.util.stream.Collectors

@Service
class CustomerService(
    private val logger: KLogger,
    private val database: DatabaseClient,
    private val queryHelper: CustomerDatabaseQuery,
    private val dataHandler: CustomerDataHandler
) : CustomerRepository {

    override suspend fun createOrEditCustomer(input: CreateOrEditCustomerInput, token: UUID): Optional<Customer> =
        database.sql(queryHelper.onCreateOrEditCustomer(input = input, token = token))
            .map { row, meta -> dataHandler.one(row = row, meta = meta) }
            .first()
            .onErrorResume {
                logger.warn { "+CustomerService -> createOrEditCustomer -> error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { Optional.empty() }

    override suspend fun addEntityToCustomer(input: AddEntityToCustomerInput, token: UUID): Optional<Customer> =
        database.sql(queryHelper.onAddEntityToCustomer(input = input, token = token))
            .map { row, meta -> dataHandler.one(row = row, meta = meta) }
            .first()
            .onErrorResume {
                logger.warn { "+CustomerService -> addEntityToCustomer -> error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { Optional.empty() }

    override suspend fun removeEntityOfCustomer(input: RemoveEntityOfCustomerInput, token: UUID): Optional<Customer> =
        database.sql(queryHelper.onRemoveEntityOfCustomer(input = input, token = token))
            .map { row, meta -> dataHandler.one(row = row, meta = meta) }
            .first()
            .onErrorResume {
                logger.warn { "+CustomerService -> removeEntityOfCustomer -> error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { Optional.empty() }

    override suspend fun deleteCustomer(input: UUID, token: UUID): Int =
        database.sql(queryHelper.onDeleteCustomer(input = input, token = token))
            .map { row, meta -> dataHandler.count(row = row, meta = meta) }
            .first()
            .onErrorResume {
                logger.warn { "+CustomerService -> deleteCustomer -> error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { 0 }

    override suspend fun retrieveAllCustomer(first: Int, after: UUID?, token: UUID): MutableList<Customer> =
        database.sql(queryHelper.onRetrieveAllCustomer(token = token, first = first, after = after))
            .map { row, meta -> dataHandler.all(row = row, meta = meta) }
            .first()
            .onErrorResume {
                logger.warn { "+CustomerService -> retrieveAllCustomer -> error = ${it.message}" }
                throw it
            }
            .awaitFirstOrElse { mutableListOf() }

    override suspend fun retrieveCustomerOfOrder(
        ids: Set<UUID>,
        userId: UUID,
        page: Int,
        size: Int
    ): Map<UUID, Customer?> = Flux.fromIterable(ids)
        .flatMap { id ->
            return@flatMap database.sql(queryHelper.onRetrieveCustomerOfOrder(input = id))
                .map { row, meta -> dataHandler.one(row = row, meta = meta) }
                .first()
                .map { AbstractMap.SimpleEntry(id, it) }
        }
        .collect(Collectors.toList())
        .map {
            val map = mutableMapOf<UUID, Customer?>()
            it.forEach { element -> map[element.key] = element.value.orElse(null) }
            return@map map.toMap()
        }
        .onErrorResume {
            logger.warn { "+CustomerService -> retrieveCustomerOfOrder -> error = ${it.message}" }
            throw it
        }
        .awaitFirstOrElse { emptyMap() }

    override suspend fun retrieveEntitiesOfCustomer(
        ids: Set<UUID>,
        userId: UUID,
        page: Int,
        size: Int
    ): Map<UUID, MutableList<Customer>> = Flux.fromIterable(ids)
        .flatMap { id ->
            return@flatMap database.sql(queryHelper.onRetrieveEntitiesOfCustomer(input = id))
                .map { row, meta -> dataHandler.all(row = row, meta = meta) }
                .first()
                .map { AbstractMap.SimpleEntry(id, it) }
        }
        .collect(Collectors.toList())
        .map { list ->
            val map = mutableMapOf<UUID, MutableList<Customer>>()
            list.filterNotNull().forEach { element -> map[element.key] = element.value }
            return@map map.toMap()
        }
        .onErrorResume {
            logger.warn { "+CustomerService -> retrieveEntitiesOfCustomer -> error = ${it.message}" }
            throw it
        }
        .awaitFirstOrElse { emptyMap() }

    override suspend fun retrieveOneCustomer(id: UUID, token: UUID): Optional<Customer> =
        database.sql(queryHelper.onRetrieveOneCustomer(input = id, token = token))
            .map { row, meta -> dataHandler.one(row = row, meta = meta) }
            .first()
            .onErrorResume {
                logger.warn { "+CustomerService -> retrieveOneCustomer -> error = ${it.message}" }
                throw it
            }.awaitFirstOrElse { Optional.empty() }
}
