package dev.hiorcraft.nex.motd.paper.utils

import dev.slne.surf.surfapi.bukkit.api.permission.PermissionRegistry

object PermissionRegistry : PermissionRegistry() {

    private const val PREFIX = "nex.motd"
    private const val COMMAND_PREFIX = "$PREFIX.command"

    val COMMAND_MOTD = create("$COMMAND_PREFIX.motd")
    val BYPASS_MAINTENANCE = create("$PREFIX.bypass")
}
