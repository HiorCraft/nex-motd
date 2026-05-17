package dev.hiorcraft.nex.motd.paper.service

import dev.hiorcraft.nex.motd.api.MotdProfile
import dev.hiorcraft.nex.motd.api.MotdProfileManager
import dev.hiorcraft.nex.motd.paper.config.MotdConfig
import dev.hiorcraft.nex.motd.paper.config.ProfileRepository

object MotdService : MotdProfileManager {

    override fun getActiveProfile(): MotdProfile? =
        ProfileRepository.get(MotdConfig.getConfig().activeProfile)

    override fun setActiveProfile(name: String) {
        MotdConfig.edit { activeProfile = name }
    }

    override fun getProfiles(): Map<String, MotdProfile> = ProfileRepository.getAll()
}
