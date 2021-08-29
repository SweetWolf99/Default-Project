package kr.kro.minestar.murder

import kr.kro.minestar.murder.functions.CreatureClass
import kr.kro.minestar.murder.functions.GameSystem
import kr.kro.minestar.murder.functions.MapClass
import kr.kro.minestar.murder.functions.Rotate
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.io.File

class CMD : CommandExecutor, TabCompleter {
    private val args0 = listOf("test", "map", "creature", "lock", "start", "stop")
    private val argsCreature = listOf("set", "reset")
    private val argsSlayerOrSacrificer = listOf("slayer", "sacrificer")
    private val argsMap = listOf("create", "teleport", "tp", "delete", "del")
    private val rotate = listOf("SOUTH", "NORTH", "WAST", "EAST")
    override fun onCommand(p: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {
        if (p !is Player) return false
        if (args.isEmpty()) {
            p.sendMessage("null")
            return false
        } else {
            when (args[0]) {
                "test" -> {
                    val item = ItemStack(Material.FLINT)
                    val itemMeta = item.itemMeta
                    itemMeta.setDisplayName("§f권총 부품 [§cPARTS§f]")
                    item.itemMeta = itemMeta
                    item.amount = 10
                    p.inventory.addItem(item)
                }

                "map" -> {
                    val map = MapClass()
                    if (args.size == 1) {

                    }
                    when (args[1]) {
                        "create", "add" -> {
                            if (args.size != 4) p.sendMessage("${Main.prefix} /murder map create <Rotate> <MapName>")
                            else map.createMap(p, args[3], Rotate.valueOf(args[2].toUpperCase()))
                        }
                        "teleport", "tp" -> {
                            if (args.size != 3) p.sendMessage("${Main.prefix} /murder map teleport <MapName>")
                            else map.teleportMap(p, map.getMap(args[2]))
                        }
                        "delete", "del" -> {
                            if (args.size != 3) p.sendMessage("${Main.prefix} /murder map delete <MapName>")
                            else map.deleteMap(p, map.getMap(args[2]))
                        }
                    }
                }

                "creature" -> {
                    if (args.size == 1) {

                    }
                    when (args[1]) {
                        "set" -> {
                            var b = false
                            if (args.size == 3) {
                                if (args[2] == "slayer") CreatureClass().setSlayer(CreatureClass().randomSlayer(p))
                                else if (args[2] == "sacrificer") CreatureClass().setSacrificer(CreatureClass().randomSacrificer(p))
                                else b = true
                            } else if (args.size == 4) {
                                if (args[2] == "slayer") CreatureClass().setSlayer(CreatureClass().getSlayer(p, args[3]))
                                else if (args[2] == "sacrificer") CreatureClass().setSacrificer(CreatureClass().getSacrificer(p, args[3]))
                                else b = true
                            } else b = true
                            if (b) p.sendMessage("${Main.prefix} /murder creature set [slayer or sacrificer] {CreatureName}")
                        }
                    }
                }

                "lock" -> {
                    if (GameSystem.gameEvent == null) GameSystem().eventLockOn(p)
                    else GameSystem().eventLockOff(p)
                }

                "start" -> GameSystem().start(p)
                "stop" -> GameSystem().gameOver(p)
            }
        }
        return false
    }

    override fun onTabComplete(p: CommandSender, cmd: Command, alias: String, args: Array<out String>): MutableList<String> {
        val list = mutableListOf<String>()
        val maps = File(Main.pl!!.dataFolder.toString() + "/map").listFiles()
        if (args.size == 1) for (s in args0) if (s.contains(args[0])) list.add(s)
        if (args.size > 1) when (args[0]) {
            "map" -> {
                if (args.size == 2) for (s in argsMap) if (s.contains(args[1])) list.add(s)
                when (args[1]) {
                    "create" -> if (args.size == 3) for (s in rotate) if (s.contains(args[2].toUpperCase())) list.add(s)

                    "teleport", "tp",
                    "delete", "del" -> if (args.size == 3) for (s in maps) if (s.name.replace(".yml", "").contains(args[2])) list.add(s.name.replace(".yml", ""))
                }
            }

            "creature" -> {
                if (args.size == 2) for (s in argsCreature) if (s.contains(args[1])) list.add(s)
                if (args.size == 3) if (args[1] == "set") for (s in argsSlayerOrSacrificer) if (s.contains(args[2])) list.add(s)
                if (args.size == 4) {
                    if (args[2] == "slayer") for (s in CreatureClass().getSlayerList()) if (s.contains(args[3])) list.add(s)
                    if (args[2] == "sacrificer") for (s in CreatureClass().getSacrificerList()) if (s.contains(args[3])) list.add(s)
                }
            }
        }

        return list
    }

}