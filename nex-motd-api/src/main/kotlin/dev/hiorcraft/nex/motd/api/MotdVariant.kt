package dev.hiorcraft.nex.motd.api

import org.spongepowered.configurate.objectmapping.ConfigSerializable

@ConfigSerializable
data class MotdVariant(
    var firstLine: String = "",
    var secondLine: String = ""
)
