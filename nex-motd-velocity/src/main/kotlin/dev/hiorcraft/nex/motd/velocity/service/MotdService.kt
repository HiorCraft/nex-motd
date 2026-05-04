package dev.hiorcraft.nex.motd.velocity.service

import dev.hiorcraft.nex.motd.api.MotdProfile
import dev.hiorcraft.nex.motd.api.MotdProfileManager
import dev.hiorcraft.nex.motd.velocity.config.MotdConfig

object MotdService : MotdProfileManager {

    override fun getActiveProfile(): MotdProfile? {
        val config = MotdConfig.getConfig()
        return config.profiles[config.activeProfile]
    }

    override fun setActiveProfile(name: String) {
        MotdConfig.edit { activeProfile = name }
    }

    override fun getProfiles(): Map<String, MotdProfile> = MotdConfig.getConfig().profiles
}
