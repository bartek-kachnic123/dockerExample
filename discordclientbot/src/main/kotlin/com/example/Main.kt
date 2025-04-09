package com.example

import com.example.discordbot.client.DiscordBot
import com.example.discordbot.config.Configuration
import io.ktor.http.*
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>){


    val config = Configuration()
    val botConfig = config.createDiscordBotConfig()

    val bot = DiscordBot(botConfig)

    if (args.size > 1 && args[0] == "-m") {
        val message = args.sliceArray(1 until args.size).joinToString(" ")

        runBlocking {
            val response = bot.sendMessage(message)

            if (response.status.isSuccess()) {
                println("Message sent successfully!")
            } else {
                println("Failed to send message. Status code: ${response.status}")
            }
        }
    }
    else {
        runBlocking {
            bot.handleEvents()
        }
    }

    bot.clean()

}