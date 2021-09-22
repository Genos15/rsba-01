package com.rsba.order_microservice.configuration.security

import graphql.kickstart.execution.subscriptions.SubscriptionSession
import graphql.kickstart.execution.subscriptions.apollo.ApolloSubscriptionConnectionListener
import graphql.kickstart.execution.subscriptions.apollo.OperationMessage
import mu.KLogger
import org.springframework.stereotype.Component

@Component
class AuthenticationConnectionListener(private val logger: KLogger) : ApolloSubscriptionConnectionListener {

    override fun onConnect(session: SubscriptionSession?, message: OperationMessage?) {
        logger.warn { "+AuthenticationConnectionListener -> onConnect" }
        super.onConnect(session, message)
    }

    override fun onStart(session: SubscriptionSession?, message: OperationMessage?) {
        logger.warn { "+AuthenticationConnectionListener -> onStart" }
        super.onStart(session, message)
    }

    override fun onStop(session: SubscriptionSession?, message: OperationMessage?) {
        logger.warn { "+AuthenticationConnectionListener -> onStop" }
        super.onStop(session, message)
    }

    override fun onTerminate(session: SubscriptionSession?, message: OperationMessage?) {
        logger.warn { "+AuthenticationConnectionListener -> onTerminate" }
        super.onTerminate(session, message)
    }
}