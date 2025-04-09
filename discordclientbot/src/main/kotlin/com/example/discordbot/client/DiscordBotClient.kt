package com.example.discordbot.client

import com.example.discordbot.commands.CommandFactory
import com.example.discordbot.config.DiscordBotConfiguration
import com.example.discordbot.controller.CategoryController
import com.example.discordbot.data.Category
import discord4j.common.util.Snowflake
import discord4j.core.DiscordClient
import discord4j.core.GatewayDiscordClient
import discord4j.core.event.ReactiveEventAdapter
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import discord4j.core.`object`.command.ApplicationCommandInteractionOption
import discord4j.core.`object`.command.ApplicationCommandInteractionOptionValue
import discord4j.discordjson.json.ApplicationCommandRequest
import discord4j.rest.RestClient
import discord4j.rest.interaction.GuildCommandRegistrar
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import reactor.core.publisher.Mono


class DiscordBot(private val config: DiscordBotConfiguration) {

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
            })
        }
    }

    private val discordClient = DiscordClient.create(config.token);
    private val commandFactory = CommandFactory()
    private val categoryController = CategoryController()

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

        return response
    }

    fun handleEvents() {
        val gateway = discordClient.login().block() ?: return
        val sendCommand = commandFactory.createSendCommand()
        val categoriesCommand = commandFactory.createCategoriesCommand()

        registerCommands(gateway.restClient, listOf(sendCommand, categoriesCommand))

        setEvents(gateway)

    }

    private fun setEvents(gateway: GatewayDiscordClient) {
        gateway.on(object : ReactiveEventAdapter() {

            override fun onChatInputInteraction(event: ChatInputInteractionEvent): Mono<Void> {
                if (event.commandName == "send") {
                    val eventMessage = event.interaction.commandInteraction.get()
                    val message = eventMessage.getOption("message").flatMap(ApplicationCommandInteractionOption::getValue)
                        .map(ApplicationCommandInteractionOptionValue::asString).get()

                    println("Reveived: $message")
                    return event.reply("Odpowiedź została odebrana.").withEphemeral(true)

                }
                else if (event.commandName == "categories") {
                    val categoriesData = categoryController.getCategoryNames().joinToString("\n")
                    return event.reply(categoriesData)
                }
                return Mono.empty()
            }
        }).blockLast()
    }

    private fun registerCommands(restClient: RestClient, list: List<ApplicationCommandRequest>) {
        GuildCommandRegistrar.create(restClient, list)
            .registerCommands(Snowflake.of(config.guildId))
            .doOnError { e -> println("Unable to create guild command: $e") }
            .onErrorResume { Mono.empty() }
            .blockLast()
    }

    fun clean() {
        client.close()
    }
}



