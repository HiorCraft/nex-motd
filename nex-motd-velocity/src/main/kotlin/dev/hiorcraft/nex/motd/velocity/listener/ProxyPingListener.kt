package dev.hiorcraft.nex.motd.velocity.listener

import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyPingEvent
import com.velocitypowered.api.proxy.server.ServerPing
import com.velocitypowered.api.util.Favicon
import dev.hiorcraft.nex.motd.velocity.plugin
import dev.hiorcraft.nex.motd.velocity.proxy
import dev.hiorcraft.nex.motd.velocity.service.MotdService
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer

object ProxyPingListener {

    private val faviconCache = mutableMapOf<String, Favicon>()

    private fun loadFavicon(iconPath: String): Favicon? {
        faviconCache[iconPath]?.let { return it }
        return try {
            val path = plugin.dataPath.resolve(iconPath)
            if (!path.toFile().exists()) return null
            Favicon.create(path).also { faviconCache[iconPath] = it }
        } catch (_: Exception) {
            null
        }
    }

    @Subscribe(priority = Short.MAX_VALUE)
    fun onProxyPing(event: ProxyPingEvent) {
        val profile = MotdService.getActiveProfile() ?: return

        val (firstLine, secondLine) = profile.resolveLines()
        val motd = "$firstLine\n$secondLine"

        event.ping = event.ping.asBuilder().apply {
            description(MiniMessage.miniMessage().deserialize(motd))
            version(
                ServerPing.Version(
                    1,
                    if (!profile.blockJoins) {
                        proxy.configuration.showMaxPlayers.toString()
                    } else {
                        LegacyComponentSerializer.legacySection().serialize(
                            MiniMessage.miniMessage().deserialize(profile.versionString)
                        )
                    }
                )
            )
            if (profile.maxPlayers > 0) {
                maximumPlayers(profile.maxPlayers)
            }
            profile.icon?.let { loadFavicon(it) }?.let { favicon(it) }
        }.build()
    }
}
