package kr.kro.minestar.murder.functions

import kr.kro.minestar.murder.Main
import org.bukkit.Bukkit
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar
import org.bukkit.scheduler.BukkitTask

class BossBar {
    companion object {
        var bossBar: BossBar? = null
        var bukkitTask: BukkitTask? = null
    }

    fun timeGauge(title: String?, tick: Int) {
        var fullTime = tick
        var timeLeft = tick
        bukkitTask = Bukkit.getScheduler().runTaskTimer(Main.pl!!, Runnable {
            if (bossBar != null) bossBar!!.removeAll()
            if (timeLeft == 0) {
                bukkitTask!!.cancel()
                bukkitTask = null
            } else {
                bossBar = Bukkit.createBossBar(title, BarColor.RED, BarStyle.SOLID)
                bossBar!!.isVisible = true
                bossBar!!.progress = timeLeft!!.toDouble() / fullTime!!.toDouble()
                for (p in Bukkit.getOnlinePlayers()) bossBar!!.addPlayer(p)
                timeLeft -= 2
            }
        }, 1, 2)
    }
}