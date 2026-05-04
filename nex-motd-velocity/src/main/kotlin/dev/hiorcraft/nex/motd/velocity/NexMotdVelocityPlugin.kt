package dev.hiorcraft.nex.motd.velocity

import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import dev.hiorcraft.nex.motd.api.NexMotdApi
import dev.hiorcraft.nex.motd.velocity.command.MotdCommand
import dev.hiorcraft.nex.motd.velocity.listener.LoginListener
import dev.hiorcraft.nex.motd.velocity.listener.MotdPluginMessageListener
import dev.hiorcraft.nex.motd.velocity.listener.ProxyPingListener
import dev.hiorcraft.nex.motd.velocity.service.MotdService
import java.nio.file.Path

class NexMotdVelocityPlugin @Inject constructor(
    val proxy: ProxyServer,
    @param:DataDirectory val dataPath: Path
) {

    init {
        instance = this
    }

    @Subscribe
    fun onProxyInitialize(event: ProxyInitializeEvent) {
        NexMotdApi.profileManager = MotdService
        proxy.channelRegistrar.register(MotdPluginMessageListener.CHANNEL)
        proxy.eventManager.register(this, ProxyPingListener)
        proxy.eventManager.register(this, LoginListener)
        proxy.eventManager.register(this, MotdPluginMessageListener)
        proxy.commandManager.register(
            proxy.commandManager.metaBuilder("motd").plugin(this).build(),
            MotdCommand()
        )
    }

    companion object {
        lateinit var instance: NexMotdVelocityPlugin
    }
}

val plugin get() = NexMotdVelocityPlugin.instance
val proxy get() = plugin.proxy
