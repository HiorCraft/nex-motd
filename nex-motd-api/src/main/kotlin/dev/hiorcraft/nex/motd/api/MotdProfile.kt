package dev.hiorcraft.nex.motd.api

import org.spongepowered.configurate.objectmapping.ConfigSerializable

@ConfigSerializable
data class MotdProfile(
    var firstLine: String = "",
    var secondLine: String = "",
    var versionString: String = "1.21.11",
    var maxPlayers: Int = -1
)
