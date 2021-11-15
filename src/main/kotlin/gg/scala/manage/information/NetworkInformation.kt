package gg.scala.manage.information

/**
 * @author GrowlyX
 * @since 11/14/2021
 */
data class NetworkInformation(
    val serverName: String,

    val primary: String,
    val secondary: String,

    val discord: String,
    val twitter: String,
    val domain: String,
    val store: String,
)
