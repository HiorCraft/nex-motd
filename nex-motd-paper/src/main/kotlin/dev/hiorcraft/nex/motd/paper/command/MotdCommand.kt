package dev.hiorcraft.nex.motd.paper.command

import dev.hiorcraft.nex.motd.paper.config.MotdConfig
import dev.hiorcraft.nex.motd.paper.config.ProfileRepository
import dev.hiorcraft.nex.motd.paper.plugin
import dev.hiorcraft.nex.motd.paper.service.MotdService
import dev.hiorcraft.nex.motd.paper.utils.PermissionRegistry
import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.arguments.StringArgument
import dev.jorel.commandapi.kotlindsl.anyExecutor
import dev.jorel.commandapi.kotlindsl.argument
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.literalArgument
import dev.slne.surf.api.core.messages.adventure.sendText
import org.bukkit.Bukkit

fun motdCommand() = commandTree("motd") {
    withAliases("nexmotd")
    withPermission(PermissionRegistry.COMMAND_MOTD)

    anyExecutor { executor, _ ->
        executor.sendText {
            secondary("/motd set <profil> | /motd list | /motd get | /motd reload")
        }
    }

    literalArgument("set") {
        argument(StringArgument("profile").replaceSuggestions(ArgumentSuggestions.strings {
            MotdService.getProfiles().keys.toTypedArray()
        })) {
            anyExecutor { executor, args ->
                val name = args[0] as String
                if (name !in MotdService.getProfiles()) {
                    executor.sendText {
                        appendErrorPrefix()
                        error("Unbekanntes Profil: ")
                        primary(name)
                    }
                    return@anyExecutor
                }
                MotdService.setActiveProfile(name)
                val player = Bukkit.getOnlinePlayers().firstOrNull()
                player?.sendPluginMessage(plugin, "nexmotd:profile", name.toByteArray(Charsets.UTF_8))
                executor.sendText {
                    appendSuccessPrefix()
                    success("MOTD-Profil gesetzt auf ")
                    primary(name)
                }
                if (player == null) {
                    executor.sendText {
                        appendWarningPrefix()
                        warning("Kein Spieler online – Velocity konnte nicht synchronisiert werden.")
                    }
                }
            }
        }
    }

    literalArgument("list") {
        anyExecutor { executor, _ ->
            val active = MotdConfig.getConfig().activeProfile
            executor.sendText { secondary("Verfügbare Profile:") }
            MotdService.getProfiles().keys.forEach { name ->
                if (name == active) {
                    executor.sendText { success("  ✔ $name") }
                } else {
                    executor.sendText { secondary("  ○ $name") }
                }
            }
        }
    }

    literalArgument("get") {
        anyExecutor { executor, _ ->
            executor.sendText {
                secondary("Aktives Profil: ")
                primary(MotdConfig.getConfig().activeProfile)
            }
        }
    }

    literalArgument("reload") {
        anyExecutor { executor, _ ->
            MotdConfig.reloadFromFile()
            ProfileRepository.reload()
            executor.sendText {
                appendSuccessPrefix()
                success("Konfiguration erfolgreich neu geladen.")
            }
        }
    }
}
