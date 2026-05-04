package dev.hiorcraft.nex.motd.paper.command

import dev.hiorcraft.nex.motd.paper.config.MotdConfig
import dev.hiorcraft.nex.motd.paper.plugin
import dev.hiorcraft.nex.motd.paper.service.MotdService
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class MotdCommand : Command("motd", "Verwalte das MOTD", "/motd", listOf("nexmotd")) {

    private val mm = MiniMessage.miniMessage()

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<String>): Boolean {
        if (!sender.hasPermission("nexmotd.admin")) {
            sender.sendMessage(mm.deserialize("<red>Keine Berechtigung."))
            return true
        }

        if (args.isEmpty()) {
            sender.sendMessage(mm.deserialize("<gray>/motd set <white><profil> <dark_gray>| <gray>/motd list <dark_gray>| <gray>/motd get"))
            return true
        }

        when (args[0].lowercase()) {
            "set" -> {
                if (args.size < 2) {
                    sender.sendMessage(mm.deserialize("<red>Verwendung: /motd set <profil>"))
                    return true
                }
                val name = args[1]
                if (name !in MotdService.getProfiles()) {
                    sender.sendMessage(mm.deserialize("<red>Unbekanntes Profil: <white>$name"))
                    return true
                }
                MotdService.setActiveProfile(name)
                sender.sendMessage(mm.deserialize("<green>MOTD-Profil gesetzt auf <white>$name<green>."))
                val player = Bukkit.getOnlinePlayers().firstOrNull()
                if (player != null) {
                    player.sendPluginMessage(plugin, "nexmotd:profile", name.toByteArray(Charsets.UTF_8))
                    sender.sendMessage(mm.deserialize("<gray>Velocity-Profil ebenfalls synchronisiert."))
                } else {
                    sender.sendMessage(mm.deserialize("<yellow>Warnung: Kein Spieler online – Velocity konnte nicht synchronisiert werden."))
                }
            }
            "list" -> {
                val active = MotdConfig.getConfig().activeProfile
                sender.sendMessage(mm.deserialize("<gray>Verfügbare Profile:"))
                MotdService.getProfiles().keys.forEach { name ->
                    val marker = if (name == active) "<green>✔" else "<dark_gray>○"
                    sender.sendMessage(mm.deserialize("  $marker <white>$name"))
                }
            }
            "get" -> {
                sender.sendMessage(mm.deserialize("<gray>Aktives Profil: <white>${MotdConfig.getConfig().activeProfile}"))
            }
            else -> sender.sendMessage(mm.deserialize("<red>Unbekannter Unterbefehl."))
        }
        return true
    }

    override fun tabComplete(sender: CommandSender, alias: String, args: Array<String>): MutableList<String> {
        if (!sender.hasPermission("nexmotd.admin")) return mutableListOf()
        return when {
            args.size <= 1 -> mutableListOf("set", "list", "get")
                .filter { it.startsWith(args.getOrElse(0) { "" }, ignoreCase = true) }
                .toMutableList()
            args.size == 2 && args[0].equals("set", ignoreCase = true) ->
                MotdService.getProfiles().keys
                    .filter { it.startsWith(args[1], ignoreCase = true) }
                    .toMutableList()
            else -> mutableListOf()
        }
    }
}
