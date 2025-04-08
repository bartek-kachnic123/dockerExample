package com.example.discordbot.client

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*

import com.example.discordbot.config.DiscordBotConfiguration;
import io.ktor.client.engine.cio.*
import kotlinx.serialization.json.Json

class DiscordBot(private val config: DiscordBotConfiguration) {

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
            })
        }
    }

    suspend fun sendMessage(message: String): HttpResponse {
        val response: HttpResponse = client.post("${config.apiUrl}/channels/${config.channelId}/messages") {
            headers {
                append(HttpHeaders.Authorization, "Bot ${config.token}")
                append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            }
            setBody("""
                {
                    "content": "$message"
                }
            """)
        }

        return response;
    }

    fun close() {
        client.close()
    }
}


