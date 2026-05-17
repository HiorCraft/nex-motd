package dev.hiorcraft.nex.motd.velocity.config

import dev.hiorcraft.nex.motd.velocity.plugin
import dev.slne.surf.api.core.config.SpongeYmlConfigClass
import org.spongepowered.configurate.objectmapping.ConfigSerializable

@ConfigSerializable
data class MotdConfig(
    var activeProfile: String = "default",
    var defaultIcons: MutableList<String> = mutableListOf("icons/icon1.png"),
) {
    companion object : SpongeYmlConfigClass<MotdConfig>(
        MotdConfig::class.java,
        plugin.dataPath,
        "config.yml"
    )
}
