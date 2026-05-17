package dev.hiorcraft.nex.motd.velocity.config

import dev.hiorcraft.nex.motd.api.MotdProfile
import dev.hiorcraft.nex.motd.api.MotdVariant
import dev.hiorcraft.nex.motd.velocity.plugin
import org.spongepowered.configurate.objectmapping.ObjectMapper
import org.spongepowered.configurate.yaml.NodeStyle
import org.spongepowered.configurate.yaml.YamlConfigurationLoader

object ProfileRepository {

    private var cache: Map<String, MotdProfile>? = null

    private val profilesDir get() = plugin.dataPath.resolve("profiles")

    private fun loaderFor(name: String) = YamlConfigurationLoader.builder()
        .path(profilesDir.resolve("$name.yml"))
        .nodeStyle(NodeStyle.BLOCK)
        .indent(2)
        .defaultOptions { opts ->
            opts.serializers { b -> b.registerAnnotatedObjects(ObjectMapper.factory()) }
        }
        .build()

    fun getAll(): Map<String, MotdProfile> = cache ?: loadAll().also { cache = it }

    fun get(name: String): MotdProfile? = getAll()[name]

    fun reload() {
        cache = null
    }

    fun save(name: String, profile: MotdProfile) {
        profilesDir.toFile().mkdirs()
        val loader = loaderFor(name)
        val node = loader.createNode()
        node.set(MotdProfile::class.java, profile)
        loader.save(node)
        cache = null
    }

    private fun loadAll(): Map<String, MotdProfile> {
        val dir = profilesDir.toFile()
        dir.mkdirs()
        if (dir.listFiles { f -> f.extension == "yml" }.isNullOrEmpty()) saveDefaults()
        return dir.listFiles { f -> f.extension == "yml" }
            ?.mapNotNull { file ->
                val profile = loadFromFile(file.nameWithoutExtension)
                if (profile == null) {
                    file.delete()
                    null
                } else {
                    file.nameWithoutExtension to profile
                }
            }
            ?.toMap()
            ?: emptyMap()
    }

    private fun loadFromFile(name: String): MotdProfile? =
        runCatching {
            loaderFor(name).load().get(MotdProfile::class.java) ?: MotdProfile()
        }.getOrNull()

    private fun saveDefaults() {
        defaultProfiles.forEach { (name, profile) -> save(name, profile) }
    }

    private val defaultProfiles: Map<String, MotdProfile> = mapOf(
        "default" to MotdProfile(
            variants = mutableListOf(
                MotdVariant(
                    firstLine = "                  <gradient:#55FF55:#1a5c1a><b>Hexoria Network</b></gradient>",
                    secondLine = "<dark_gray>        » <green>Erkunde</green> <dark_gray>•</dark_gray><green>Kämpfe</green> <dark_gray>•</dark_gray> <green>Herrsche</green> <dark_gray>«"
                ),
                MotdVariant(
                    firstLine = "                  <gradient:#55FF55:#1a5c1a><b>Hexoria Network</b></gradient>",
                    secondLine = "                <gray><b>News: <gold>New Parkour"
                )
            )
        ),
        "maintenance" to MotdProfile(
            firstLine = "<red>              <dark_gray>- <red><b>Wartungsarbeiten</b><dark_gray> -<red>",
            secondLine = " <gray>               Wir sind bald wieder da",
            versionString = "⚙ Wartung",
            blockJoins = true,
            showVersionString = true,
            kickMessage = "<red>Der Server befindet sich aktuell in Wartung. Komm später wieder!"
        ),
        "event" to MotdProfile(
            firstLine = "<gradient:#FFD700:#FF8C00>✦ NEX-NETWORK EVENT ✦</gradient>",
            secondLine = "<yellow>» <white>Besonderes Event läuft gerade!</white> «"
        ),
        "whitelist" to MotdProfile(
            firstLine = "<dark_red><b>NEX-NETWORK</b></dark_red> <dark_gray>- <white>Whitelist aktiv</white>",
            secondLine = "<gray>Nur eingeladene Spieler können beitreten.",
            versionString = "⚠ Whitelist",
            showVersionString = true
        ),
        "weekend" to MotdProfile(
            firstLine = "<gradient:#a8ff78:#78ffd6>✦ NEX-NETWORK WEEKEND ✦</gradient>",
            secondLine = "<green>» <white>Doppelte XP & Sonderevents!</white> «"
        )
    )
}
