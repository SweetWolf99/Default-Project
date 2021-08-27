package kr.kro.minestar.murder.creature.slayer.skill.active

import kr.kro.minestar.murder.creature.interfaces.skill.ActiveSkill
import kr.kro.minestar.murder.creature.interfaces.skill.Type
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitTask
import org.bukkit.util.Vector

class Dash(override var player: Player) : ActiveSkill {
    override var number: Int = 1
    override var name: String = "대쉬"
    override var codeName: String = this.javaClass.name
    override var description = mutableListOf(
        "§f§7전방으로 돌진합니다.",
        "§f§7하지만 어림도 없지"
    )
    override var coolTime: Long = 20 * 5
    override var coolDown: Long = 0
    override var coolDownTimer: BukkitTask? = null
    override var duration: Long = 20 * 0
    override var type: Type = Type.MOVEMENT

    override fun active() {
        if (coolDownLock()) return
        val x = player.location.direction.x
        val z = player.location.direction.z
        val v = Vector(x, 0.0, z).normalize()
        player.velocity = v.add(Vector(0, 0, 0)).multiply(5).setY(0.5)
        coolDownTimer()
    }
}