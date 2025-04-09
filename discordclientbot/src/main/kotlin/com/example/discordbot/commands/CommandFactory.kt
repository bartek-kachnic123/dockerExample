package com.example.discordbot.commands

import discord4j.core.`object`.command.ApplicationCommandOption
import discord4j.discordjson.json.ApplicationCommandOptionData
import discord4j.discordjson.json.ApplicationCommandRequest
import discord4j.discordjson.json.ImmutableApplicationCommandRequest

class CommandFactory {

    fun createSendCommand(): ImmutableApplicationCommandRequest {
        val sendCommand = ApplicationCommandRequest.builder()
            .name("send")
            .description("Send a message")
            .addOption(
                ApplicationCommandOptionData.builder()
                    .name("message")
                    .description("text message")
                    .type(ApplicationCommandOption.Type.STRING.value)
                    .required(true)
                    .build())
            .build()

        return sendCommand;
    }
}