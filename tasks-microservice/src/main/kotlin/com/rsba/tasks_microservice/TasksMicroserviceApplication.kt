package com.rsba.tasks_microservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableConfigurationProperties
@EnableAspectJAutoProxy
@EnableScheduling
@EnableCaching
class TasksMicroserviceApplication

fun main(args: Array<String>) {
    runApplication<TasksMicroserviceApplication>(*args)
}
