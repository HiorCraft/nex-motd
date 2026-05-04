package dev.hiorcraft.nex.motd.paper.service

import dev.hiorcraft.nex.motd.api.MotdProfile
import dev.hiorcraft.nex.motd.api.MotdProfileManager
import dev.hiorcraft.nex.motd.api.redis.MotdProfileChangeEvent
import dev.hiorcraft.nex.motd.paper.config.MotdConfig
import dev.hiorcraft.nex.motd.paper.redis.redisApi
import dev.hiorcraft.nex.motd.paper.redis.redisLoader

object MotdService : MotdProfileManager {

    override fun getActiveProfile(): MotdProfile? {
        val name = redisLoader.activeProfile.get()
        return MotdConfig.getConfig().profiles[name]
    }

    override fun setActiveProfile(name: String) {
        redisLoader.activeProfile.set(name)
        redisApi.publishEvent(MotdProfileChangeEvent(name))
    }

    override fun getProfiles(): Map<String, MotdProfile> = MotdConfig.getConfig().profiles
}
