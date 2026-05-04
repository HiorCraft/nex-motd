package dev.hiorcraft.nex.motd.velocity.config

import dev.hiorcraft.nex.motd.api.MotdProfile
import dev.hiorcraft.nex.motd.velocity.plugin
import dev.slne.surf.api.core.config.SpongeYmlConfigClass
import org.spongepowered.configurate.objectmapping.ConfigSerializable

@ConfigSerializable
data class MotdConfig(
    var activeProfile: String = "default",
    var profiles: MutableMap<String, MotdProfile> = mutableMapOf(
        "default" to MotdProfile(
            firstLine = "<gradient:#f7b733:#fc4a1a><b>NEX-NETWORK</b></gradient>",
            secondLine = "<gray>» <white>Spielen</white> <dark_gray>•</dark_gray> <white>Erkunden</white> <dark_gray>•</dark_gray> <white>Erleben</white> «",
            versionString = "✦ 1.21.11",
            maxPlayers = -1
        ),
        "maintenance" to MotdProfile(
            firstLine = "<red><b>NEX-NETWORK</b></red> <dark_gray>- <red><b>Wartungsarbeiten</b></red>",
            secondLine = "<gray>Wir sind gleich zurück. Schau im Discord vorbei!",
            versionString = "⚙ Wartung"
        ),
        "event" to MotdProfile(
            firstLine = "<gradient:#FFD700:#FF8C00>✦ NEX-NETWORK EVENT ✦</gradient>",
            secondLine = "<yellow>» <white>Besonderes Event läuft gerade!</white> «",
            versionString = "★ Event aktiv"
        ),
        "whitelist" to MotdProfile(
            firstLine = "<dark_red><b>NEX-NETWORK</b></dark_red> <dark_gray>- <white>Whitelist aktiv</white>",
            secondLine = "<gray>Nur eingeladene Spieler können beitreten.",
            versionString = "⚠ Whitelist"
        ),
        "weekend" to MotdProfile(
            firstLine = "<gradient:#a8ff78:#78ffd6>✦ NEX-NETWORK WEEKEND ✦</gradient>",
            secondLine = "<green>» <white>Doppelte XP & Sonderevents!</white> «",
            versionString = "✦ Weekend Special"
        )
    )
) {
    companion object : SpongeYmlConfigClass<MotdConfig>(
        MotdConfig::class.java,
        plugin.dataPath,
        "config.yml"
    )
}
