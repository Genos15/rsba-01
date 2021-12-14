package com.rsba.parameters_microservice.domain.format

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class ModelType(val _class: String, val version: Int = 0)