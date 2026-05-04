package dev.hiorcraft.nex.motd.velocity.service

import dev.hiorcraft.nex.motd.api.MotdProfile
import dev.hiorcraft.nex.motd.api.MotdProfileManager
import dev.hiorcraft.nex.motd.api.redis.MotdProfileChangeEvent
import dev.hiorcraft.nex.motd.velocity.config.MotdConfig
import dev.hiorcraft.nex.motd.velocity.redis.redisApi
import dev.hiorcraft.nex.motd.velocity.redis.redisLoader

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
