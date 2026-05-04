package dev.hiorcraft.nex.motd.velocity.listener

import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyPingEvent
import com.velocitypowered.api.proxy.server.ServerPing
import dev.hiorcraft.nex.motd.velocity.service.MotdService
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer

object ProxyPingListener {

    @Subscribe(priority = Short.MAX_VALUE)
    fun onProxyPing(event: ProxyPingEvent) {
        val profile = MotdService.getActiveProfile() ?: return

        val motd = "${profile.firstLine}\n${profile.secondLine}"

        event.ping = event.ping.asBuilder().apply {
            description(MiniMessage.miniMessage().deserialize(motd))
            version(
                ServerPing.Version(
                    1,
                    LegacyComponentSerializer.legacySection().serialize(
                        MiniMessage.miniMessage().deserialize(profile.versionString)
                    )
                )
            )
            if (profile.maxPlayers > 0) {
                maximumPlayers(profile.maxPlayers)
            }
        }.build()
    }
}
