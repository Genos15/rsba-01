package com.rsba.usermicroservice.configuration.database

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory
import org.springframework.data.mongodb.core.convert.MappingMongoConverter
import org.springframework.data.mongodb.gridfs.ReactiveGridFsTemplate


@Configuration
class ReactiveMongoConfig {

    @Bean
    fun reactiveGridFsTemplate(
        reactiveMongoDbFactory: ReactiveMongoDatabaseFactory,
        mappingMongoConverter: MappingMongoConverter
    ): ReactiveGridFsTemplate? {
        return ReactiveGridFsTemplate(reactiveMongoDbFactory, mappingMongoConverter)
    }

}