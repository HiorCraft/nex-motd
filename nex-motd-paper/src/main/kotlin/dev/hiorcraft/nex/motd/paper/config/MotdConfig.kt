package dev.hiorcraft.nex.motd.paper.config

import dev.hiorcraft.nex.motd.api.MotdProfile
import dev.hiorcraft.nex.motd.api.MotdVariant
import dev.hiorcraft.nex.motd.paper.plugin
import dev.slne.surf.api.core.config.SpongeYmlConfigClass
import org.spongepowered.configurate.objectmapping.ConfigSerializable

@ConfigSerializable
data class MotdConfig(
    var activeProfile: String = "default",
    var profiles: MutableMap<String, MotdProfile> = mutableMapOf(
        "default" to MotdProfile(
            versionString = "✦ 1.21.11",
            maxPlayers = -1,
            variants = mutableListOf(
                MotdVariant(
                    firstLine = "               <gradient:#55FF55:#1a5c1a><b>Hexoria</b></gradient>",
                    secondLine = "<dark_gray>        » <green>Erkunde</green> <dark_gray>•</dark_gray> <green>Kämpfe</green> <dark_gray>•</dark_gray> <green>Herrsche</green> <dark_gray>«"
                ),
                MotdVariant(
                    firstLine = "         <gradient:#1a5c1a:#55FF55><b>✦ Hexoria ✦</b></gradient>",
                    secondLine = "            <dark_green>✦ <green>Dein nächstes Abenteuer wartet<dark_green>... ✦"
                )
            )
        ),
        "maintenance" to MotdProfile(
            firstLine = "<red><b>NEX-NETWORK</b></red> <dark_gray>- <red><b>Wartungsarbeiten</b></red>",
            secondLine = "<gray>Wir sind gleich zurück. Schau im Discord vorbei!",
            versionString = "⚙ Wartung",
            blockJoins = true,
            kickMessage = "<red>Der Server befindet sich aktuell in Wartung. Komm später wieder!"
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
