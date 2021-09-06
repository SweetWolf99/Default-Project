package kr.kro.minestar.murder.`object`.skill.slayer.active

import kr.kro.minestar.murder.Main
import kr.kro.minestar.murder.interfaces.Type
import kr.kro.minestar.murder.interfaces.skill.ActiveSkill
import org.bukkit.Bukkit
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.entity.ShulkerBullet
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.scheduler.BukkitTask
import java.util.*

class ImpactGrenade(override var player: Player) : ActiveSkill {
    init {
        Bukkit.getPluginManager().registerEvents(this, Main.pl!!)
        Bukkit.getScheduler().runTask(Main.pl!!, Runnable { startCoolDownTimer() })
    }

    override var number: Int = 3
    override var name: String = "충격 수류탄"
    override var codeName: String = this.javaClass.name

    override var coolTime: Long = 20 * 30
    override var coolDown: Long = coolTime
    override var coolDownTimer: BukkitTask? = null
    override var duration: Long = 20 * 0
    override var type: Type = Type.DISTURBING

    private var grenade: ShulkerBullet? = null
    private val damage = 25.0

    override var description = mutableListOf(
        "충격 수류탄을 던집니다",
        " ",
        "최대 대미지 : $damage",
    )

    override fun active() {
        if (coolDownLock()) return
        player.world.playSound(player.location, Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.MASTER, 1.0F, 2.0F)
        grenade = player.world.spawnEntity(player.eyeLocation, EntityType.SHULKER_BULLET) as ShulkerBullet
        val v = player.eyeLocation.direction.multiply(1.0)
        grenade!!.velocity = v
        coolDownTimer()
    }

    @EventHandler
    fun projectileHit(e: ProjectileHitEvent) {
        val entity = e.entity
        val loc = entity.location
        if (entity !is ShulkerBullet) return
        if (entity != grenade) return
        val players = entity.location.getNearbyPlayers(3.0)
        entity.world.playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.MASTER, 1.0F, 0.7F)
        entity.world.spawnParticle(Particle.EXPLOSION_LARGE, loc, 15, 1.0, 1.0, 1.0, 0.0)
        for (p in players) {

            val d1 = loc.distance(p.location)
            val d2 = loc.distance(p.location.clone().add(0.0, 0.9, 0.0))
            val d3 = loc.distance(p.location.clone().add(0.0, 1.8, 0.0))
            val array = arrayOf(d1, d2, d3)
            Arrays.sort(array)
            if (array[0] <= 1.0) array[1] = 1.0
            if (p != player)
                if (e.hitEntity != null) {
                    if (e.hitEntity!!.uniqueId == p.uniqueId) p.damage(damage)
                } else p.damage(damage / array[1])
        }
    }
}