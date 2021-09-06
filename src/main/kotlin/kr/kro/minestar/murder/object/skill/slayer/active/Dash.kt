package kr.kro.minestar.murder.`object`.skill.slayer.active

import kr.kro.minestar.murder.Main
import kr.kro.minestar.murder.interfaces.skill.ActiveSkill
import kr.kro.minestar.murder.interfaces.Type
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitTask
import org.bukkit.util.Vector

class Dash(override var player: Player) : ActiveSkill {
    init {
        Bukkit.getPluginManager().registerEvents(this, Main.pl!!)
        Bukkit.getScheduler().runTask(Main.pl!!, Runnable { startCoolDownTimer() })
    }
    override var number: Int = 1
    override var name: String = "대쉬"
    override var codeName: String = this.javaClass.name
    override var description = mutableListOf(
        "전방으로 돌진합니다"
    )
    override var coolTime: Long = 20 * 10
    override var coolDown: Long = coolTime
    override var coolDownTimer: BukkitTask? = null
    override var duration: Long = 20 * 0
    override var type: Type = Type.MOVEMENT

    override fun active() {
        if (coolDownLock()) return
        val x = player.location.direction.x
        val z = player.location.direction.z
        val v = Vector(x, 0.0, z).normalize()
        player.velocity = v.add(Vector(0, 0, 0)).multiply(2).setY(0.5)
        coolDownTimer()
    }
}