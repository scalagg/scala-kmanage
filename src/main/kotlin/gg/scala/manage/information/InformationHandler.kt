package gg.scala.manage.information

import com.solexgames.datastore.commons.connection.impl.mongo.UriMongoConnection
import com.solexgames.datastore.commons.layer.impl.MongoStorageLayer

/**
 * @author GrowlyX
 * @since 11/14/2021
 */
object InformationHandler
{
    lateinit var handle: MongoStorageLayer<NetworkInformation>

    fun initialLoad()
    {
        handle = MongoStorageLayer(
            UriMongoConnection(
                "mongodb://127.0.0.1:27017/Scala"
            ), "Scala", "minecraft",
            NetworkInformation::class.java
        )
    }
}
