package dev.hiorcraft.nex.motd.paper

import dev.hiorcraft.nex.motd.api.NexMotdApi
import dev.hiorcraft.nex.motd.paper.listener.ServerListPingListener
import dev.hiorcraft.nex.motd.paper.redis.redisLoader
import dev.hiorcraft.nex.motd.paper.service.MotdService
import org.bukkit.plugin.java.JavaPlugin
import java.nio.file.Path

class NexMotdPaperPlugin : JavaPlugin() {

    val dataPath: Path get() = dataFolder.toPath()

    override fun onEnable() {
        instance = this
        NexMotdApi.profileManager = MotdService
        redisLoader.connect()
        server.pluginManager.registerEvents(ServerListPingListener, this)
    }

    override fun onDisable() {
        redisLoader.disconnect()
    }

    companion object {
        lateinit var instance: NexMotdPaperPlugin
    }
}

val plugin get() = NexMotdPaperPlugin.instance
