package com.rsba.order_microservice.data.publisher

import com.rsba.order_microservice.database.*
import com.rsba.order_microservice.domain.model.OrderForSub
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.*
import kotlinx.coroutines.reactive.awaitFirstOrNull
import mu.KLogger
import org.reactivestreams.Publisher
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import reactor.core.publisher.*
import reactor.util.concurrent.Queues
import java.util.*

@Component
class OrderPublisher(
    private val logger: KLogger,
    private val database: DatabaseClient,
    private val queryHelper: OrderDatabaseQuery,
    private val categoryDataHandler: CategoryDataHandler,
    private val agentDataHandler: AgentDataHandler,
    private val forCustomer: CustomerDatabaseQuery,
    private val customerDataHandler: CustomerDataHandler
) : InMenRepository {

    private var sink: Sinks.Many<OrderForSub> =
        Sinks.many().multicast().onBackpressureBuffer(Queues.SMALL_BUFFER_SIZE, false)

    private var sinkMessage: Sinks.Many<UserMessage> =
        Sinks.many().multicast().onBackpressureBuffer(Queues.SMALL_BUFFER_SIZE, false)

    var processor: Flux<OrderForSub> = sink.asFlux()

    private lateinit var processorMessage: Flux<UserMessage>

    private val uuidPublisher: ConnectableFlux<UUID>
    private lateinit var uuidStream: FluxSink<UUID>

    init {
        val myUuidPublisher = Flux.create<UUID>({
            uuidStream = it
        }, FluxSink.OverflowStrategy.BUFFER)
        uuidPublisher = myUuidPublisher.publish()
        uuidPublisher.map {

        }.subscribe { }
        uuidPublisher.connect {}
//        uuidStream.next()
        processorMessage = sinkMessage.asFlux()
    }
//    val t: FluxSink<Any> =

//    val subscribers: ConcurrentMultiRegistry<String, FluxSink<Any>> = ConcurrentMultiRegistry()

//    var sink2: FluxSink<Any> = processor.sink(FluxSink.OverflowStrategy.IGNORE)


    fun publishMessage(message: UserMessage) = try {
        logger.warn { "+--- OrderPublisher -> publish" }

        GlobalScope.async {
            sinkMessage.asFlux().subscribe {

            }
//            sink.tryEmitNext(order).orThrow()
        }
    } catch (e: Exception) {
        logger.warn { "+--- OrderPublisher -> publish -> error = ${e.message}" }
    }

    fun publish(order: OrderForSub) = try {
        logger.warn { "+--- OrderPublisher -> publish" }

        GlobalScope.async {
            val catsFlux = database.sql(queryHelper.onRetrieveCategoriesOfOrder(input = order.id))
                .map { row, meta -> categoryDataHandler.all(row = row, meta = meta) }
                .first()

            val managerFlux = database.sql(queryHelper.onRetrieveManagerOfOrder(input = order.id))
                .map { row, meta -> agentDataHandler.one(row = row, meta = meta) }
                .first()

            val customerFlux = database.sql(forCustomer.onRetrieveCustomerOfOrder(input = order.id))
                .map { row, meta -> customerDataHandler.one(row = row, meta = meta) }
                .first()

            Mono.zip(catsFlux, managerFlux) { cats, manager ->
                order.categories = cats
                order.manager = manager?.get()
            }.zipWith(customerFlux) { _, customer ->
                order.customer = customer?.get()
            }.onErrorResume { Mono.empty() }.awaitFirstOrNull()

            sink.tryEmitNext(order).orThrow()
        }
    } catch (e: Exception) {
        logger.warn { "+--- OrderPublisher -> publish -> error = ${e.message}" }
    }

    fun get(environment: DataFetchingEnvironment?): Publisher<OrderForSub> {
        logger.warn { "+--- OrderPublisher -> get" }
        return processor
    }

    fun getAs(environment: DataFetchingEnvironment?): Publisher<UserMessage> {
        return processorMessage
    }

    override fun getUUIDPublisher(): Publisher<UUID> {
        TODO("Not yet implemented")
    }
}
