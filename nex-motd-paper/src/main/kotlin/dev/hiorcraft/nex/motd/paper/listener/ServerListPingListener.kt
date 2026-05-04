package dev.hiorcraft.nex.motd.paper.listener

import com.destroystokyo.paper.event.server.PaperServerListPingEvent
import dev.hiorcraft.nex.motd.paper.service.MotdService
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

object ServerListPingListener : Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onServerListPing(event: PaperServerListPingEvent) {
        val profile = MotdService.getActiveProfile() ?: return

        val motd = "${profile.firstLine}\n${profile.secondLine}"
        event.motd(MiniMessage.miniMessage().deserialize(motd))

        if (profile.maxPlayers > 0) {
            event.maxPlayers = profile.maxPlayers
        }
    }
}
