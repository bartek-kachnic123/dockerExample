package com.example.discordbot.config

data class DiscordBotConfiguration(
    val token: String,
    val channelId: String,
    val guildId: String,
    val apiUrl: String = "https://discord.com/api/v10",
    val userAgent: String = "Ktor DiscordBot"
)
