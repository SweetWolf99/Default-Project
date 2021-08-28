package kr.kro.minestar.murder

import kr.kro.minestar.murder.functions.SetCreature
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList

class CMD : CommandExecutor, TabCompleter {
    private val args0 = listOf("test1")
    override fun onCommand(p: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {
        if (p !is Player) return false
        if (args.isEmpty()) {
            p.sendMessage("null")
            return false
        } else {
            when (args[0]) {
                "test1" -> SetCreature().setSlayer(p)
                "test2" -> SetCreature().setSacrificer(p)
                "test3" -> HandlerList.unregisterAll()
            }
        }
        return false
    }

    override fun onTabComplete(p: CommandSender, cmd: Command, alias: String, args: Array<out String>): MutableList<String> {
        val list = mutableListOf<String>()
        if (args.size == 1) for (s in args0) if (s.contains(args[0])) list.add(s)

        return list
    }

}