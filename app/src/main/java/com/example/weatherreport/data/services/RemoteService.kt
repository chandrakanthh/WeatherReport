package com.example.weatherreport.data.services

import com.example.weatherreport.ui.utils.Constants
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.http.*
import kotlin.jvm.Throws

class RemoteService() {
    val httpClient = HttpClient(Android) {
        install(JsonFeature) {
            val json = kotlinx.serialization.json.Json {
                ignoreUnknownKeys = true
                isLenient = true
            }
            serializer = KotlinxSerializer(json)
        }

        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
    }

    @Throws(Exception::class)
    fun getUrl(endpoint: String): Url = URLBuilder(Constants.baseUrl + endpoint).build()

    @Throws(Exception::class)
    fun addHeaders(headersBuilder: HeadersBuilder) {
        headersBuilder.apply {
            append("content-type", "application/json")
        }
    }

}