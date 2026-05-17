package dev.hiorcraft.nex.motd.api

import org.spongepowered.configurate.objectmapping.ConfigSerializable

@ConfigSerializable
data class MotdProfile(
    var firstLine: String? = null,
    var secondLine: String? = null,
    var versionString: String = "1.21.11",
    var icon: String? = null,
    var icons: MutableList<String> = mutableListOf(),
    var maxPlayers: Int = -1,
    var blockJoins: Boolean = false,
    var showVersionString: Boolean = false,
    var kickMessage: String = "<red>Der Server befindet sich derzeit in Wartung.",
    var variants: MutableList<MotdVariant> = mutableListOf()
) {
    fun resolveLines(): Pair<String, String> =
        if (variants.isEmpty()) (firstLine ?: "") to (secondLine ?: "")
        else variants.random().let { it.firstLine to it.secondLine }

    fun resolveIcon(): String? = when {
        icons.isNotEmpty() -> icons.random()
        icon != null -> icon
        else -> null
    }
}
