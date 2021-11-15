package gg.scala.manage

import com.google.common.io.Files
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.LongSerializationPolicy
import gg.scala.manage.command.InformationViewCommand
import gg.scala.manage.information.InformationHandler
import gg.scala.manage.information.InformationHandler.initialLoad
import io.github.revxrsal.cub.jda.JDACommandHandler
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent
import java.io.File

/**
 * @author GrowlyX
 * @since 11/14/2021
 */
object KManageApplication
{
    @JvmStatic
    val GSON: Gson = GsonBuilder()
        .setPrettyPrinting()
        .setLongSerializationPolicy(LongSerializationPolicy.STRING)
        .create()

    lateinit var client: JDA

    var configuration = KManageConfiguration()

    @JvmStatic
    fun main(args: Array<String>)
    {
        loadConfiguration()
        initialLoad()

        client = JDABuilder.createDefault(configuration.token)
            .enableIntents(
                GatewayIntent.GUILD_BANS,
                GatewayIntent.GUILD_MESSAGE_REACTIONS,
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.DIRECT_MESSAGES
            ).build()

        client.awaitReady().let {
            JDACommandHandler.create(
                it, JDACommandHandler.Settings.builder()
                    .prefix("-")
                    .stripMarkdown(false)
                    .build()
            ).registerCommand(
                InformationViewCommand
            )
        }
    }

    private fun loadConfiguration()
    {
        configuration = GSON.fromJson(
            Files.newReader(
                File("settings.json"), Charsets.UTF_8
            ),
            KManageConfiguration::class.java
        )
    }
}
