package kr.kro.minestar.murder.`object`.skill.slayer.passive

import kr.kro.minestar.murder.Main
import kr.kro.minestar.murder.interfaces.skill.PassiveSkill
import kr.kro.minestar.murder.interfaces.Type
import org.bukkit.*
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitTask

class Chase(override var player: Player) : PassiveSkill {
    override var number:Int = 1
    override var name:String = "추격"
    override var codeName: String = this.javaClass.name
    override var description = mutableListOf(
        "30블럭 이내에 있는 플레이어를",
        "바라보고 있으면 '신속 I'을 받습니다"
    )
    override var tick: BukkitTask? = null
    override var type: Type = Type.MOVEMENT

    init {
        tick = Bukkit.getScheduler().runTaskTimer(Main.pl!!, Runnable {
            var d = 0.0
            val loc: Location = player.eyeLocation
            var locD = loc.toVector().add(loc.direction.multiply(d)).toLocation(player.world)
            while (true) {
                if(d > 30) break
                d += 0.1
                if (locD.block.type != Material.AIR) break
                val players = locD.getNearbyPlayers(0.0)
                if (players.toTypedArray().isNotEmpty()) for (target in players) if (target != player) if (target.gameMode != GameMode.SPECTATOR) {
                    player.addPotionEffect(PotionEffect(PotionEffectType.SPEED, 11, 0))
                    break
                }
                locD = loc.toVector().add(loc.direction.multiply(d)).toLocation(player.world)
            }
        }, 1, 10)
    }
}