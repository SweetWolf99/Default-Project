package kr.kro.minestar.murder.functions

import kr.kro.minestar.murder.Main
import kr.kro.minestar.murder.interfaces.creature.Sacrificer
import kr.kro.minestar.murder.interfaces.creature.Slayer
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.entity.Item
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.scheduler.BukkitTask
import org.bukkit.scoreboard.Team
import org.bukkit.util.Vector
import java.util.*

class GameSystem {
    companion object {
        var playing = false
        var gameEvent: GameEvent? = null
        var checkLivePlayer: BukkitTask? = null

        var slayer = 1
        var sacrificer = 1
    }

    fun eventLockOn(p: Player?) {
        if (gameEvent == null) gameEvent = GameEvent()
        p?.sendMessage("${Main.prefix} Event Lock §aOn")
    }

    fun eventLockOff(p: Player?) {
        if (gameEvent != null) {
            HandlerList.unregisterAll(gameEvent!!)
            gameEvent = null
        }
        p?.sendMessage("${Main.prefix} Event Lock §cOff")
    }

    fun start(p: Player) {
        if (playing) {
            p.sendMessage("${Main.prefix} §c게임이 이미 진행 중입니다.")
            return
        }
        playing = true
        val fadeIn = 20
        val stay = 50
        val fadeOut = 10
        val wait = 10
        val total: Long = (fadeIn + stay + fadeOut + wait).toLong()
        eventLockOn(null)
        for (entity in Bukkit.getWorld("world")!!.entities) if (entity is Item) entity.remove()
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

        val score = Bukkit.getScoreboardManager().mainScoreboard
        var team = score.getTeam("player")
        if (team == null) {
            team = score.registerNewTeam("player")
            team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER)
            team.setCanSeeFriendlyInvisibles(false)
        }
        for (p in Bukkit.getOnlinePlayers()) {
            team.addEntry(p.name)
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
        gameEvent!!.damageCancel = false
        checkLivePlayer()
        for ((count, p) in players.withIndex()) {
            if (count == r) CreatureClass().setSlayer(CreatureClass().randomSlayer(p))
            else CreatureClass().setSacrificer(CreatureClass().randomSacrificer(p))
        }
    }

    fun checkLivePlayer() {
        if (checkLivePlayer != null) {
            checkLivePlayer!!.cancel()
            checkLivePlayer = null
        }
        checkLivePlayer = Bukkit.getScheduler().runTaskTimer(Main.pl!!, Runnable {
            var sl = 0
            var sc = 0
            for (p in Bukkit.getOnlinePlayers()) {
                val creature = CreatureClass.playerCreature[p.uniqueId]
                if (p.gameMode != GameMode.SPECTATOR) {
                    if (creature is Slayer) ++sl
                    if (creature is Sacrificer) ++sc
                }
            }
            slayer = sl
            sacrificer = sc
            if (sl == 0 || sc == 0) victory(sl, sc)
        }, 1, 10)
    }

    private fun victory(slayer: Int, sacrificer: Int) {
        checkLivePlayer!!.cancel()
        val fadeIn = 20
        val stay = 50
        val fadeOut = 10
        val wait = 40
        val total: Long = (fadeIn + stay + fadeOut + wait).toLong()
        if (sacrificer == 0) for (p in Bukkit.getOnlinePlayers()) {
            p.sendTitle("§cSlayer", "§8Victory", fadeIn, stay, fadeOut)
            if (CreatureClass.playerCreature[p.uniqueId] is Slayer) p.playSound(p.location, Sound.UI_TOAST_CHALLENGE_COMPLETE, SoundCategory.MASTER, 1F, 1.5F)
            else p.playSound(p.location, Sound.ENTITY_RAVAGER_CELEBRATE, SoundCategory.MASTER, 1F, 0.7F)
        }
        else if (slayer == 0) for (p in Bukkit.getOnlinePlayers()) {
            p.sendTitle("§9Sacrificer", "§7Victory", fadeIn, stay, fadeOut)
            if (CreatureClass.playerCreature[p.uniqueId] is Slayer) p.playSound(p.location, Sound.ENTITY_RAVAGER_CELEBRATE, SoundCategory.MASTER, 1F, 0.7F)
            else p.playSound(p.location, Sound.UI_TOAST_CHALLENGE_COMPLETE, SoundCategory.MASTER, 1F, 1.5F)
        }
        Bukkit.getScheduler().runTaskLater(Main.pl!!, Runnable { gameOver(null) }, total)
    }

    fun gameOver(player: Player?) {
        if (checkLivePlayer == null) {
            player?.sendMessage("${Main.prefix} §c크리처가 설정된 후 게임을 중단 할 수 있습니다.")
            return
        }
        if (player != null) checkLivePlayer!!.cancel()
        playing = false
        eventLockOff(null)
        checkLivePlayer = null
        val score = Bukkit.getScoreboardManager().mainScoreboard
        var team = score.getTeam("player")
        for(p in team!!.players)team.removeEntry(p.name!!)
        for (p in Bukkit.getOnlinePlayers()) {
            CreatureClass().removeClass(p)
            p.gameMode = GameMode.SURVIVAL
            p.health = 20.0
            p.foodLevel = 20
            p.inventory.clear()
            p.teleport(p.world.spawnLocation)
            var rX =Random().nextInt(20)
            var rZ =Random().nextInt(20)
            if (Random().nextBoolean()) rX * -1
            if (Random().nextBoolean()) rZ * -1
            Bukkit.getScheduler().runTaskLater(Main.pl!!, Runnable {
                p.velocity = Vector((rX.toDouble() / 10) + 2, 0.5, (rZ.toDouble() / 10) + 2)
            }, 3)
        }
    }
}