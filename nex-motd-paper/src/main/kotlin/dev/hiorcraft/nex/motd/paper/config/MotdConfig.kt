package dev.hiorcraft.nex.motd.paper.config

import dev.hiorcraft.nex.motd.paper.plugin
import dev.slne.surf.api.core.config.SpongeYmlConfigClass
import org.spongepowered.configurate.objectmapping.ConfigSerializable

@ConfigSerializable
data class MotdConfig(
    var activeProfile: String = "default"
) {
    companion object : SpongeYmlConfigClass<MotdConfig>(
        MotdConfig::class.java,
        plugin.dataPath,
        "config.yml"
    )
}
