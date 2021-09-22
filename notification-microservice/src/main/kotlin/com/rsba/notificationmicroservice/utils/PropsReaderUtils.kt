package com.rsba.notificationmicroservice.utils

import mu.KotlinLogging
import java.io.InputStream
import java.io.FileNotFoundException

import java.io.IOException
import java.lang.Exception
import java.util.*


object PropsReaderUtils {
    var inputStream: InputStream? = null

    private val logger = KotlinLogging.logger {}

    @Throws(IOException::class)
    fun getPropById(id: String): String? = try {


//        logger.warn { "ENV = ${System.getenv("user-microservice-host")}" }


        val prop = Properties()
        val propFileName = "config.properties"
        inputStream = javaClass.classLoader.getResourceAsStream(propFileName)
        if (inputStream != null) {
            prop.load(inputStream)
        } else {
            throw FileNotFoundException("property file '$propFileName' not found in the classpath")
        }
        prop.getProperty(id)
    } catch (e: Exception) {
        println("Exception: $e")
        null
    } finally {
        inputStream!!.close()
    }

}