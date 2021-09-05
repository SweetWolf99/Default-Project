package kr.kro.minestar.murder.`object`.skill.slayer.passive

import kr.kro.minestar.murder.Main
import kr.kro.minestar.murder.interfaces.Type
import kr.kro.minestar.murder.interfaces.skill.PassiveSkill
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitTask

class Hide(override var player: Player) : PassiveSkill {
    override var number: Int = 2
    override var name: String = "은신"
    override var codeName: String = this.javaClass.name
    override var description = mutableListOf(
        "구석에 움크리고 있으면 '투명화'가 됩니다"
    )
    override var tick: BukkitTask? = null
    override var type: Type = Type.MOVEMENT

    init {
        tick = Bukkit.getScheduler().runTaskTimer(Main.pl!!, Runnable {
            if (player.isSneaking) {
                val loc = player.location.block.location
                var count = 0
                if (loc.clone().add(1.0, 0.0, 0.0).block.type != Material.AIR) ++count
                if (loc.clone().add(-1.0, 0.0, 0.0).block.type != Material.AIR) ++count
                if (loc.clone().add(0.0, 0.0, 1.0).block.type != Material.AIR) ++count
                if (loc.clone().add(0.0, 0.0, -1.0).block.type != Material.AIR) ++count
                if (count >= 2) player.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY, 11, 0))
            }
        }, 1, 10)
    }
}