package dev.hiorcraft.nex.motd.api

interface MotdProfileManager {
    fun getActiveProfile(): MotdProfile?
    fun setActiveProfile(name: String)
    fun getProfiles(): Map<String, MotdProfile>
}
