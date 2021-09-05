package kr.kro.minestar.murder.`object`.item.weapon

import kr.kro.minestar.murder.Main
import kr.kro.minestar.murder.interfaces.item.Weapon
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class HiddenDagger(override var player: Player) : Weapon {
    init {
        Bukkit.getPluginManager().registerEvents(this, Main.pl!!)
    }

    override var material: Material = Material.IRON_SWORD
    override var number: Int = 2
    override var display: String = "히든 대거"
    override var damage: Double = 12.0
    override var hitEffect: List<String> = listOf("대상에게 '독 I'을 5초 부여 합니다")
    override var killEffect: List<String> = listOf("'투명화'를 5초간 받습니다")


    override fun hitEffect(e: EntityDamageByEntityEvent) {
        val target = e.entity as Player
        val damager = e.damager as Player
        target.addPotionEffect(PotionEffect(PotionEffectType.POISON,20 * 5,0))
        target.location.direction = damager.location.subtract(target.location).toVector()
    }

    override fun killEffect(e: EntityDamageByEntityEvent) {
        val target = e.entity as Player
        val damager = e.damager as Player
        damager.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY,20 * 5,0))
    }
}