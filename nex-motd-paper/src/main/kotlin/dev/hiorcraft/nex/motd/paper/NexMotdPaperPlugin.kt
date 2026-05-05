package dev.hiorcraft.nex.motd.paper

import dev.hiorcraft.nex.motd.api.NexMotdApi
import dev.hiorcraft.nex.motd.paper.command.motdCommand
import dev.hiorcraft.nex.motd.paper.listener.ServerListPingListener
import dev.hiorcraft.nex.motd.paper.service.MotdService
import org.bukkit.plugin.java.JavaPlugin
import java.nio.file.Path

class NexMotdPaperPlugin : JavaPlugin() {

    val dataPath: Path get() = dataFolder.toPath()

    override fun onEnable() {
        instance = this
        NexMotdApi.profileManager = MotdService
        server.pluginManager.registerEvents(ServerListPingListener, this)
        motdCommand().register()
        server.messenger.registerOutgoingPluginChannel(this, "nexmotd:profile")
    }

    override fun onDisable() {
        server.messenger.unregisterOutgoingPluginChannel(this, "nexmotd:profile")
    }

    companion object {
        lateinit var instance: NexMotdPaperPlugin
    }
}

val plugin get() = NexMotdPaperPlugin.instance
