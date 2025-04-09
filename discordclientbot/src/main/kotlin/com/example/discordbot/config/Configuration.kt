package com.example.discordbot.config

class Configuration {

    private val envVariables = listOf("BOT_KEY", "CHANNEL_ID", "GUILD_ID")
    private val envValues = mutableMapOf<String, String>()

    init {
        setEnvs()
    }

    private fun setEnvs() {
        for (envVar in envVariables) {
            val value = System.getenv(envVar)
            if (value.isNullOrEmpty()) {
                throw RuntimeException("Error: $envVar environment variable is not set.")
            }
            envValues[envVar] = value
        }
    }

    fun createDiscordBotConfig(): DiscordBotConfiguration {
        val botToken = envValues["BOT_KEY"]!!
        val channelId = envValues["CHANNEL_ID"]!!
        val guildId = envValues["GUILD_ID"]!!

        return DiscordBotConfiguration(
            token = botToken,
            channelId = channelId,
            guildId = guildId
        )
    }
}