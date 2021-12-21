package com.rsba.tasks_microservice.domain.exception

abstract class ErrorFitterControl {

    fun fit(message: String): Boolean {
        for (indice in indices()) {
            if (!message.contains(indice)) {
                return false;
            }
        }
        return true
    }

    abstract fun indices(): List<String>
}