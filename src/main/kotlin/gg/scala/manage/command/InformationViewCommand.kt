package gg.scala.manage.command

import gg.scala.manage.KManageApplication
import gg.scala.manage.information.InformationHandler
import io.github.revxrsal.cub.annotation.Command
import io.github.revxrsal.cub.annotation.Description
import io.github.revxrsal.cub.jda.JDACommandSubject
import net.dv8tion.jda.api.EmbedBuilder
import java.awt.Color
import java.time.Instant

/**
 * @author GrowlyX
 * @since 11/14/2021
 */
object InformationViewCommand
{
    @Command("view-all-information")
    @Description("View information for all networks.")
    fun onViewAllInformation(context: JDACommandSubject)
    {
        context.parentEvent.channel.sendMessage(
            "Fetching information..."
        )

        InformationHandler.handle.fetchAllEntries().thenAccept {
            if (it == null)
            {
                context.parentEvent.channel.sendMessage("Sorry! No entries were found :(").queue()
                return@thenAccept
            }

            val embed = EmbedBuilder()
            embed.setColor(
                Color.decode(KManageApplication.configuration.color)
            )
            embed.setTimestamp(Instant.now())

            var description = """
                Here are all registered networks:
                ```
            """.trimIndent()
            var iteration = 0

            it.values.forEach { server ->
                description += "\n"
                description += """
                    #${iteration++ + 1}: ${server.serverName} - ${server.domain}
                """.trimIndent()
            }

            description += "\n```"

            embed.setDescription(description)
            embed.setTitle("All Networks")

            context.parentEvent.channel.sendMessage(embed.build()).queue()
        }
    }

    @Command("view-information")
    @Description("View information for a target network.")
    fun onViewInformation(context: JDACommandSubject, id: String)
    {
        context.parentEvent.channel.sendMessage(
            "Fetching information..."
        )

        InformationHandler.handle.fetchEntryByKey(id).thenAccept {
            if (it == null)
            {
                context.parentEvent.channel.sendMessage("Sorry! No entry was found :(").queue()
                return@thenAccept
            }

            val embed = EmbedBuilder()
            embed.setColor(
                Color.decode(KManageApplication.configuration.color)
            )
            embed.setTimestamp(Instant.now())
            embed.setDescription(
                """
                    Data for the network, `${it.serverName}` is displayed below.
                    ```
                    ${
                    KManageApplication.GSON.toJson(it)
                    }
                    ```
                """.trimIndent()
            )
            embed.setTitle("Network")

            context.parentEvent.channel.sendMessage(embed.build()).queue()
        }
    }
}
