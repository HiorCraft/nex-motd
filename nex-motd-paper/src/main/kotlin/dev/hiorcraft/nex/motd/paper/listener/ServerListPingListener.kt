package dev.hiorcraft.nex.motd.paper.listener

import com.destroystokyo.paper.event.server.PaperServerListPingEvent
import dev.hiorcraft.nex.motd.paper.plugin
import dev.hiorcraft.nex.motd.paper.service.MotdService
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.util.CachedServerIcon

object ServerListPingListener : Listener {

    private val iconCache = mutableMapOf<String, CachedServerIcon>()

    private fun loadIcon(iconPath: String): CachedServerIcon? {
        iconCache[iconPath]?.let { return it }
        return try {
            val file = plugin.dataPath.resolve(iconPath).toFile()
            if (!file.exists()) return null
            Bukkit.loadServerIcon(file).also { iconCache[iconPath] = it }
        } catch (_: Exception) {
            null
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onServerListPing(event: PaperServerListPingEvent) {
        val profile = MotdService.getActiveProfile() ?: return

        val (firstLine, secondLine) = profile.resolveLines()
        val motd = "$firstLine\n$secondLine"
        event.motd(MiniMessage.miniMessage().deserialize(motd))

        if (profile.maxPlayers > 0) {
            event.maxPlayers = profile.maxPlayers
        }

        profile.icon?.let { loadIcon(it) }?.let { event.setServerIcon(it) }
    }
}
