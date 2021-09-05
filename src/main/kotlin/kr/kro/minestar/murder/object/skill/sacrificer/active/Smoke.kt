package kr.kro.minestar.murder.`object`.skill.sacrificer.active

import kr.kro.minestar.murder.Main
import kr.kro.minestar.murder.interfaces.Type
import kr.kro.minestar.murder.interfaces.skill.ActiveSkill
import org.bukkit.Bukkit
import org.bukkit.Particle
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitTask

class Smoke(override var player: Player) : ActiveSkill {
    init {
        Bukkit.getPluginManager().registerEvents(this, Main.pl!!)
        Bukkit.getScheduler().runTask(Main.pl!!, Runnable { startCoolDownTimer() })
    }

    override var number: Int = 3
    override var name: String = "연막"
    override var codeName: String = this.javaClass.name
    override var description = mutableListOf(
        "연기를 뿜어 시야를 차단합니다"
    )
    override var coolTime: Long = 20 * 60
    override var coolDown: Long = coolTime
    override var coolDownTimer: BukkitTask? = null
    override var duration: Long = 20 * 0
    override var type: Type = Type.DISTURBING

    var timer: BukkitTask? = null

    override fun active() {
        if (coolDownLock()) return
        val loc = player.location.clone().add(0.0, 1.5, 0.0)
        var count = 0
        timer = Bukkit.getScheduler().runTaskTimer(Main.pl!!, Runnable {
            ++count
            player.world.spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, loc, count * 10, count / 10.0, count / 30.0, count / 10.0, 0.005)
            if (count == 30) {
                timer!!.cancel()
            }
        }, 1, 1)

        coolDownTimer()
    }
}