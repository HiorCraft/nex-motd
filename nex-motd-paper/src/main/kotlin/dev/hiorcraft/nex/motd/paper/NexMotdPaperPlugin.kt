package dev.hiorcraft.nex.motd.paper

import dev.hiorcraft.nex.motd.api.NexMotdApi
import dev.hiorcraft.nex.motd.paper.command.motdCommand
import dev.hiorcraft.nex.motd.paper.listener.LoginListener
import dev.hiorcraft.nex.motd.paper.listener.ServerListPingListener
import dev.hiorcraft.nex.motd.paper.config.ProfileRepository
import dev.hiorcraft.nex.motd.paper.service.MotdService
import org.bukkit.plugin.java.JavaPlugin

class NexMotdPaperPlugin : JavaPlugin() {

    override fun onEnable() {
        instance = this
        dataPath.resolve("icons").toFile().mkdirs()
        dataPath.resolve("profiles").toFile().mkdirs()
        NexMotdApi.profileManager = MotdService
        MotdService.getProfiles()
        server.pluginManager.registerEvents(ServerListPingListener, this)
        server.pluginManager.registerEvents(LoginListener, this)
        motdCommand()
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
