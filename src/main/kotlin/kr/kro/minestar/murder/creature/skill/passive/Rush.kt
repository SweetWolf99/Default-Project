package kr.kro.minestar.murder.creature.skill.passive

import kr.kro.minestar.murder.Main
import kr.kro.minestar.murder.interfaces.skill.PassiveSkill
import kr.kro.minestar.murder.interfaces.Type
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitTask

class Rush(override var player: Player) : PassiveSkill {
    override var number:Int = 1
    override var name:String = "추격"
    override var codeName: String = this.javaClass.name
    override var description = mutableListOf(
        "이동속도가 빠릅니다",
        "내다리는 100만불짜리 다리"
    )
    override var tick: BukkitTask? = null
    override var type: Type = Type.MOVEMENT

    init {
        tick = Bukkit.getScheduler().runTaskTimer(Main.pl!!, Runnable {
            player.addPotionEffect(PotionEffect(PotionEffectType.SPEED, 10, 0, false, false, false))
        }, 10, 1)
    }
}