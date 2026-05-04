package dev.hiorcraft.nex.motd.velocity.listener

import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.PluginMessageEvent
import com.velocitypowered.api.proxy.ServerConnection
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier
import dev.hiorcraft.nex.motd.velocity.service.MotdService

object MotdPluginMessageListener {

    val CHANNEL: MinecraftChannelIdentifier = MinecraftChannelIdentifier.from("nexmotd:profile")

    @Subscribe
    fun onPluginMessage(event: PluginMessageEvent) {
        if (event.identifier != CHANNEL) return
        if (event.source !is ServerConnection) return
        event.result = PluginMessageEvent.ForwardResult.handled()
        val profileName = String(event.data, Charsets.UTF_8)
        if (profileName in MotdService.getProfiles()) {
            MotdService.setActiveProfile(profileName)
        }
    }
}