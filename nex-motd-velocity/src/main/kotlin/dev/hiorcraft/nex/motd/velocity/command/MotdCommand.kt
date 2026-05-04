package dev.hiorcraft.nex.motd.velocity.command

import com.velocitypowered.api.command.SimpleCommand
import dev.hiorcraft.nex.motd.velocity.config.MotdConfig
import dev.hiorcraft.nex.motd.velocity.service.MotdService
import net.kyori.adventure.text.minimessage.MiniMessage

class MotdCommand : SimpleCommand {

    private val mm = MiniMessage.miniMessage()

    override fun execute(invocation: SimpleCommand.Invocation) {
        val source = invocation.source()
        val args = invocation.arguments()

        if (args.isEmpty()) {
            source.sendMessage(mm.deserialize("<gray>/motd set <white><profil> <dark_gray>| <gray>/motd list <dark_gray>| <gray>/motd get"))
            return
        }

        when (args[0].lowercase()) {
            "set" -> {
                if (args.size < 2) {
                    source.sendMessage(mm.deserialize("<red>Verwendung: /motd set <profil>"))
                    return
                }
                val name = args[1]
                if (name !in MotdService.getProfiles()) {
                    source.sendMessage(mm.deserialize("<red>Unbekanntes Profil: <white>$name"))
                    return
                }
                MotdService.setActiveProfile(name)
                source.sendMessage(mm.deserialize("<green>MOTD-Profil gesetzt auf <white>$name<green>."))
            }
            "list" -> {
                val active = MotdConfig.getConfig().activeProfile
                source.sendMessage(mm.deserialize("<gray>Verfügbare Profile:"))
                MotdService.getProfiles().keys.forEach { name ->
                    val marker = if (name == active) "<green>✔" else "<dark_gray>○"
                    source.sendMessage(mm.deserialize("  $marker <white>$name"))
                }
            }
            "get" -> {
                source.sendMessage(mm.deserialize("<gray>Aktives Profil: <white>${MotdConfig.getConfig().activeProfile}"))
            }
            else -> source.sendMessage(mm.deserialize("<red>Unbekannter Unterbefehl."))
        }
    }

    override fun hasPermission(invocation: SimpleCommand.Invocation) =
        invocation.source().hasPermission("nexmotd.admin")

    override fun suggest(invocation: SimpleCommand.Invocation): List<String> {
        val args = invocation.arguments()
        return when {
            args.size <= 1 -> listOf("set", "list", "get")
                .filter { it.startsWith(args.getOrElse(0) { "" }, ignoreCase = true) }
            args.size == 2 && args[0].equals("set", ignoreCase = true) ->
                MotdService.getProfiles().keys
                    .filter { it.startsWith(args[1], ignoreCase = true) }
                    .toList()
            else -> emptyList()
        }
    }
}
