package dev.hiorcraft.nex.motd.paper.listener

import dev.hiorcraft.nex.motd.paper.service.MotdService
import dev.hiorcraft.nex.motd.paper.utils.PermissionRegistry
import net.kyori.adventure.text.minimessage.MiniMessage
import net.luckperms.api.LuckPermsProvider
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent

object LoginListener : Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPreLogin(event: AsyncPlayerPreLoginEvent) {
        val profile = MotdService.getActiveProfile() ?: return
        if (!profile.blockJoins) return

        val luckPerms = runCatching { LuckPermsProvider.get() }.getOrNull() ?: return
        val user = luckPerms.userManager.loadUser(event.uniqueId).join() ?: return

        if (user.cachedData.permissionData.checkPermission(PermissionRegistry.BYPASS_MAINTENANCE).asBoolean()) return

        event.disallow(
            AsyncPlayerPreLoginEvent.Result.KICK_OTHER,
            MiniMessage.miniMessage().deserialize(profile.kickMessage)
        )
    }
}
