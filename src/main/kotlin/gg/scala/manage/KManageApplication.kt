package gg.scala.manage

import com.google.common.io.Files
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.LongSerializationPolicy
import gg.scala.manage.information.InformationHandler
import gg.scala.manage.information.InformationHandler.initialLoad
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

    var configuration = KManageConfiguration()

    @JvmStatic
    fun main(args: Array<String>)
    {
        loadConfiguration()
        initialLoad()
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
