package kr.kro.minestar.murder.functions

import kr.kro.minestar.murder.Main
import kr.kro.minestar.murder.interfaces.creature.Sacrificer
import kr.kro.minestar.murder.interfaces.creature.Slayer
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import java.util.*

class GameSystem {
    companion object {
        var lockEvent: LockEvent? = null
    }

    fun getMember() {
        var slayer = 0
        var sacrificer = 0
        for (p in Bukkit.getOnlinePlayers()) {
            val creature = CreatureClass.playerCreature[p.uniqueId]
            if (p.gameMode != GameMode.SPECTATOR) {
                if (creature is Slayer) ++slayer
                if (creature is Sacrificer) ++sacrificer
            }
        }
        if (slayer == 0) sacrificerVictory()
        else if (sacrificer == 0) slayerVictory()
    }

    private fun slayerVictory() {
        val fadeIn = 20
        val stay = 50
        val fadeOut = 10
        val wait = 10
        val total: Long = (fadeIn + stay + fadeOut + wait).toLong()
        for (p in Bukkit.getOnlinePlayers()) {
            for (p in Bukkit.getOnlinePlayers()) p.sendTitle("§cMurder", "", fadeIn, stay, fadeOut)
        }
    }

    private fun sacrificerVictory() {
        for (p in Bukkit.getOnlinePlayers()) {

        }
    }

    fun eventLockOn(p: Player?) {
        if (lockEvent == null) lockEvent = LockEvent()
        p?.sendMessage("${Main.prefix} Event Lock §aOn")
    }

    fun eventLockOff(p: Player?) {
        if (lockEvent != null) {
            HandlerList.unregisterAll(lockEvent!!)
            lockEvent = null
        }
        p?.sendMessage("${Main.prefix} Event Lock §cOff")
    }

    fun start() {
        val fadeIn = 20
        val stay = 50
        val fadeOut = 10
        val wait = 10
        val total: Long = (fadeIn + stay + fadeOut + wait).toLong()
        eventLockOn(null)
        for (p in Bukkit.getOnlinePlayers()) p.sendTitle("§cMurder", "", fadeIn, stay, fadeOut)
        Bukkit.getScheduler().runTaskLater(Main.pl!!, Runnable { mapSelect() }, total)
    }

    private fun mapSelect() {//tp map
        val fadeIn = 0
        val stay = 50
        val fadeOut = 10
        val wait = 10
        val total: Long = (fadeIn + stay + fadeOut + wait).toLong()
        val map = MapClass().randomMap()
        MapClass().teleportMap(Bukkit.getOnlinePlayers(), map)
        for (p in Bukkit.getOnlinePlayers()) {
            p.gameMode = GameMode.SURVIVAL
            p.health = 20.0
            p.foodLevel = 20
            p.inventory.clear()
            p.sendTitle("§c${map.name.replace(".yml", "")}", "", fadeIn, stay, fadeOut)
        }
        Bukkit.getScheduler().runTaskLater(Main.pl!!, Runnable { timer() }, total)
    }

    private fun timer() {//tp map
        val time = 20 * 5

        BossBar().timeGauge("잠시 후 §c학살자§f가 정해집니다", time)

        Bukkit.getScheduler().runTaskLater(Main.pl!!, Runnable { setCreature() }, time.toLong())
    }

    private fun setCreature() {
        val players = Bukkit.getOnlinePlayers()
        val r = Random().nextInt(players.size)
        lockEvent!!.damageCancel = false
        for ((count, p) in players.withIndex()) {
            if (count == r) CreatureClass().setSlayer(CreatureClass().randomSlayer(p))
            else CreatureClass().setSacrificer(CreatureClass().randomSacrificer(p))
        }
    }
}