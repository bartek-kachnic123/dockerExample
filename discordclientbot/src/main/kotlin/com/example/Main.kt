package com.example

import com.example.discordbot.client.DiscordBot
import com.example.discordbot.config.DiscordBotConfiguration
import io.ktor.http.*
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>){

    val botToken = System.getenv("BOT_KEY")
    if (botToken == null) {
        println("Error: BOT_KEY environment variable is not set.")
        return
    }

    val channelId = System.getenv("CHANNEL_ID")
    if (channelId == null) {
        println("Error: ChannelId environment variable is not set.")
    }

    if (args.size < 2 || args[0] != "-m") {
        return
    }
    val message = args.sliceArray(1 until args.size).joinToString(" ")

    val botConfig = DiscordBotConfiguration(token = botToken, channelId = channelId)

    val bot = DiscordBot(botConfig)

    runBlocking {
        val response = bot.sendMessage(message)

        if (response.status.isSuccess()) {
            println("Message sent successfully!")
        } else {
            println("Failed to send message. Status code: ${response.status}")
        }

        bot.close()
    }
}