package kr.kro.minestar.murder.functions

import kr.kro.minestar.murder.Main
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.entity.Player

class GameSystem {
    companion object{
        var lockEvent: LockEvent? = null
    }
    private val players: Collection<Player> = Bukkit.getOnlinePlayers()

    fun start() {
        val fadeIn = 20
        val stay = 50
        val fadeOut = 10
        val wait = 10
        val total: Long = (fadeIn + stay + fadeOut + wait).toLong()

        for (p in players) p.sendTitle("§cMurder", "", fadeIn, stay, fadeOut)
        Bukkit.getScheduler().runTaskLater(Main.pl!!, Runnable { ready1() }, total)
    }

    private fun ready1() {//tp map
        val fadeIn = 0
        val stay = 50
        val fadeOut = 10
        val wait = 10
        val total: Long = (fadeIn + stay + fadeOut + wait).toLong()

        val map = MapClass().randomMap()
        MapClass().teleportMap(players, map)
        for (p in players) {
            p.gameMode = GameMode.SURVIVAL
            lockEvent = LockEvent()
            p.sendTitle("§c${map.name.replace(".yml", "")}", "", fadeIn, stay, fadeOut)
        }
        Bukkit.getScheduler().runTaskLater(Main.pl!!, Runnable {  }, total)
    }
}