package dev.hiorcraft.nex.motd.velocity.utils

object PermissionRegistry {

    private const val PREFIX = "nex.motd"
    private const val COMMAND_PREFIX = "$PREFIX.command"

    const val COMMAND_MOTD = "$COMMAND_PREFIX.motd"
    const val BYPASS_MAINTENANCE = "$PREFIX.bypass"
}
