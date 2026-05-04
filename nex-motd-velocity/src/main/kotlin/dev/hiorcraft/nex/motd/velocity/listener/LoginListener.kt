package dev.hiorcraft.nex.motd.velocity.listener

import com.velocitypowered.api.event.ResultedEvent
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.LoginEvent
import dev.hiorcraft.nex.motd.velocity.service.MotdService
import net.kyori.adventure.text.minimessage.MiniMessage

object LoginListener {

    @Subscribe
    fun onLogin(event: LoginEvent) {
        val profile = MotdService.getActiveProfile() ?: return
        if (!profile.blockJoins) return
        if (event.player.hasPermission("nexmotd.bypass")) return
        event.result = ResultedEvent.ComponentResult.denied(
            MiniMessage.miniMessage().deserialize(profile.kickMessage)
        )
    }
}
