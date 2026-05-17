package dev.hiorcraft.nex.motd.velocity.service

import dev.hiorcraft.nex.motd.api.MotdProfile
import dev.hiorcraft.nex.motd.api.MotdProfileManager
import dev.hiorcraft.nex.motd.velocity.config.MotdConfig
import dev.hiorcraft.nex.motd.velocity.config.ProfileRepository

object MotdService : MotdProfileManager {

    override fun getActiveProfile(): MotdProfile? =
        ProfileRepository.get(MotdConfig.getConfig().activeProfile)

    override fun setActiveProfile(name: String) {
        MotdConfig.edit { activeProfile = name }
    }

    override fun getProfiles(): Map<String, MotdProfile> = ProfileRepository.getAll()
}
