package kr.kro.minestar.murder.`object`.item.weapon

import kr.kro.minestar.murder.Main
import kr.kro.minestar.murder.`object`.creature.slayer.BlastManiac
import kr.kro.minestar.murder.functions.CreatureClass
import kr.kro.minestar.murder.interfaces.item.Weapon
import org.bukkit.*
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.util.Vector

class BlastHammer(override var player: Player) : Weapon {
    init {
        Bukkit.getPluginManager().registerEvents(this, Main.pl!!)
    }

    override var material: Material = Material.IRON_AXE
    override var number: Int = 3
    override var display: String = "폭발 망치"
    override var damage: Double = 10.0
    override var hitEffect: List<String> = listOf("대상이 폭발하며 날아갑니다")
    override var killEffect: List<String> = listOf("'폭발 부스터'를 얻습니다")


    override fun hitEffect(e: EntityDamageByEntityEvent) {
        val target = e.entity as Player
        val x = player.location.direction.x
        val z = player.location.direction.z
        val v = Vector(x, 0.0, z).normalize()
        target.velocity = v.add(Vector(0, 0, 0)).multiply(1).setY(1.5)
        player.world.playSound(player.location, Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.MASTER, 1.0F, 1.0F)
        player.world.spawnParticle(Particle.EXPLOSION_LARGE, player.location, 5, 0.4, 0.0, 0.4, 0.0)
    }

    override fun killEffect(e: EntityDamageByEntityEvent) {
        val target = e.entity as Player
        player.inventory.addItem((CreatureClass.playerCreature[player.uniqueId] as BlastManiac).tool!!.getTool())
    }
}