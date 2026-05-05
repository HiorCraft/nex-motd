package dev.hiorcraft.nex.motd.velocity.command

import com.velocitypowered.api.command.SimpleCommand
import dev.hiorcraft.nex.motd.velocity.config.MotdConfig
import dev.hiorcraft.nex.motd.velocity.service.MotdService
import dev.hiorcraft.nex.motd.velocity.utils.PermissionRegistry
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText

class MotdCommand : SimpleCommand {

    override fun execute(invocation: SimpleCommand.Invocation) {
        val source = invocation.source()
        val args = invocation.arguments()

        if (args.isEmpty()) {
            source.sendText {
                secondary("/motd set <profil> | /motd list | /motd get | /motd reload")
            }
            return
        }

        when (args[0].lowercase()) {
            "set" -> {
                if (args.size < 2) {
                    source.sendText {
                        appendErrorPrefix()
                        error("Verwendung: /motd set <profil>")
                    }
                    return
                }
                val name = args[1]
                if (name !in MotdService.getProfiles()) {
                    source.sendText {
                        appendErrorPrefix()
                        error("Unbekanntes Profil: ")
                        primary(name)
                    }
                    return
                }
                MotdService.setActiveProfile(name)
                source.sendText {
                    appendSuccessPrefix()
                    success("MOTD-Profil gesetzt auf ")
                    primary(name)
                }
            }
            "list" -> {
                val active = MotdConfig.getConfig().activeProfile
                source.sendText { secondary("Verfügbare Profile:") }
                MotdService.getProfiles().keys.forEach { name ->
                    if (name == active) {
                        source.sendText { success("  ✔ $name") }
                    } else {
                        source.sendText { secondary("  ○ $name") }
                    }
                }
            }
            "get" -> {
                source.sendText {
                    secondary("Aktives Profil: ")
                    primary(MotdConfig.getConfig().activeProfile)
                }
            }
            "reload" -> {
                MotdConfig.reloadFromFile()
                source.sendText {
                    appendSuccessPrefix()
                    success("Konfiguration erfolgreich neu geladen.")
                }
            }
            else -> source.sendText {
                appendErrorPrefix()
                error("Unbekannter Unterbefehl.")
            }
        }
    }

    override fun hasPermission(invocation: SimpleCommand.Invocation) =
        invocation.source().hasPermission(PermissionRegistry.COMMAND_MOTD)

    override fun suggest(invocation: SimpleCommand.Invocation): List<String> {
        val args = invocation.arguments()
        return when {
            args.size <= 1 -> listOf("set", "list", "get", "reload")
                .filter { it.startsWith(args.getOrElse(0) { "" }, ignoreCase = true) }
            args.size == 2 && args[0].equals("set", ignoreCase = true) ->
                MotdService.getProfiles().keys
                    .filter { it.startsWith(args[1], ignoreCase = true) }
                    .toList()
            else -> emptyList()
        }
    }
}
