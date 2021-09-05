package kr.kro.minestar.murder.`object`.skill.slayer.active

import kr.kro.minestar.murder.Main
import kr.kro.minestar.murder.interfaces.Type
import kr.kro.minestar.murder.interfaces.skill.ActiveSkill
import org.bukkit.*
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitTask

class DaggerSpray(override var player: Player) : ActiveSkill {
    init {
        Bukkit.getPluginManager().registerEvents(this, Main.pl!!)
        Bukkit.getScheduler().runTask(Main.pl!!, Runnable { startCoolDownTimer() })
    }

    override var number: Int = 2
    override var name: String = "단검 분사"
    override var codeName: String = this.javaClass.name
    override var description = mutableListOf(
        "5블럭 이내에 있는 플레이어에게",
        "단검을 던져 '독 II'을 5초간 부여 합니다"
    )
    override var coolTime: Long = 20 * 1
    override var coolDown: Long = coolTime
    override var coolDownTimer: BukkitTask? = null
    override var duration: Long = 20 * 0
    override var type: Type = Type.DISTURBING

    override fun active() {
        if (coolDownLock()) return
        val players = player!!.location.getNearbyPlayers(5.0)
        for (p in players) {
            if (p != player) {
                var d = 0.0
                val loc = player.eyeLocation.setDirection(p.eyeLocation.subtract(player.eyeLocation).toVector())
                var locD = loc.toVector().add(loc.direction.multiply(d)).toLocation(player.world)
                while (true) {
                    if (d > 5) break
                    d += 0.1
                    if (locD.block.type != Material.AIR) break
                    val targets = locD.getNearbyPlayers(0.0)
                    if (targets.toTypedArray().isNotEmpty())
                        for (target in targets)
                            if (target != player)
                                if (target.gameMode != GameMode.SPECTATOR) {
                                    target.addPotionEffect(PotionEffect(PotionEffectType.POISON, 20 * 5, 1))
                                    break
                                }
                    locD = loc.toVector().add(loc.direction.multiply(d)).toLocation(player.world)
                }
            }
        }
        player.world.playSound(player.location, Sound.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.MASTER, 1.0F, 1.2F)
        Bukkit.getScheduler().runTaskLater(Main.pl!!, Runnable { player.world.playSound(player.location, Sound.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.MASTER, 0.7F, 1.2F) }, 3)
        Bukkit.getScheduler().runTaskLater(Main.pl!!, Runnable { player.world.playSound(player.location, Sound.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.MASTER, 0.7F, 1.2F) }, 6)
        coolDownTimer()
    }
}